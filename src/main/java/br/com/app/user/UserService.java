package br.com.app.user;

import br.com.app.exception.BadRequestException;
import br.com.app.exception.ResourceNotFoundException;
import br.com.app.permission.PermissionService;
import br.com.app.permission.model.Permission;
import br.com.app.permission.model.PermissionEntity;
import br.com.app.security.JwtUserDetailsFactory;
import br.com.app.security.SecurityUtils;
import br.com.app.user.dto.UserChangePasswordDto;
import br.com.app.user.model.UserEntity;
import br.com.app.user.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PermissionService permissionService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUsername(username);
        if (user != null) {
            return JwtUserDetailsFactory.create(user);
        }
        throw new UsernameNotFoundException("Usuário " + username + " não encontrado");
    }

    @Transactional
    public void createDefaultUser() {
        if (!repository.existsByUsername("admin")) {
            PermissionEntity permission = permissionService.findByPermission(Permission.ADMIN);

            UserEntity user = new UserEntity();
            user.setUsername("admin");
            user.setPassword(securityUtils.generatePassword("zxC@123"));
            user.setEnabled(true);
            user.setLocked(false);
            user.setLoginAttempts(0);
            user.setPermissions(List.of(permission));
            repository.save(user);
        }
    }

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Transactional
    public void loginAttempts(String username) {
        UserEntity user = findByUsername(username);
        if (user != null) {
            user.setLoginAttempts(user.getLoginAttempts() + 1);
            if (user.getLoginAttempts() >= 3) {
                user.setLocked(true);
            }
            repository.save(user);
        }
    }

    @Transactional
    public void loginSuccess(UserEntity user) {
        user.setLoginAttempts(0);
        repository.save(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UserEntity createUser(String userName, String password) {
        if (repository.existsByUsername(userName)) {
            throw new BadRequestException("Usuário informado já está cadastrado");
        }

        PermissionEntity permission = permissionService.findByPermission(Permission.USER);

        UserEntity user = new UserEntity();
        user.setUsername(userName);
        user.setPassword(securityUtils.generatePassword(password));
        user.setEnabled(true);
        user.setLocked(false);
        user.setLoginAttempts(0);
        user.setPermissions(List.of(permission));
        return repository.save(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void disableUser(long id) {
        repository.disableUser(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void enableUser(long id) {
        repository.enableUser(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void addPermissionUser(UserEntity user, Set<Permission> permissions) {
        List<Permission> userPermissions = user.getPermissions().stream().map(PermissionEntity::getPermission).collect(Collectors.toList());

        List<Permission> resPermissions = permissions.stream().map(p -> {
            boolean result = userPermissions.stream().anyMatch(up -> up.equals(p));
            if (!result) {
                return p;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(resPermissions)) {
            List<PermissionEntity> entities = permissionService.findByPermissionIn(resPermissions);

            entities.addAll(user.getPermissions());
            user.setPermissions(entities);
            repository.save(user);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void removePermissionUser(UserEntity user, Set<Permission> permissions) {
        List<Permission> userPermissions = user.getPermissions().stream().map(PermissionEntity::getPermission).collect(Collectors.toList());

        List<Permission> resPermissions = userPermissions.stream().map(up -> {
            if (up.equals(Permission.USER)) {
                return up;
            }
            boolean result = permissions.stream().anyMatch(p -> p.equals(up));
            if (result) {
                return null;
            }
            return up;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        List<PermissionEntity> entities = permissionService.findByPermissionIn(resPermissions);
        user.setPermissions(entities);
        repository.save(user);
    }

    @Transactional
    public String changePassword(String username, UserChangePasswordDto dto) {
        if (dto.getPassword().equals(dto.getNewPassword())) {
            throw new BadRequestException("As senhas informadas são iguais");
        }
        UserEntity user = repository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("Usuário " + username + " não encontrado");
        }
        if (!securityUtils.verifyPassword(dto.getPassword(), user.getPassword())) {
            throw new BadRequestException("A senha atual informada está incorreta");
        }
        user.setPassword(securityUtils.generatePassword(dto.getNewPassword()));
        repository.save(user);
        return "Senha alterada com sucesso";
    }

}
