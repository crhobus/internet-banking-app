package br.com.app.security;

import br.com.app.permission.model.PermissionEntity;
import br.com.app.user.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserDetailsFactory {

    private JwtUserDetailsFactory() {}

    public static JwtUserDetails create(UserEntity user) {
        return new JwtUserDetails(user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getPermissions()), user.isEnabled(), user.isLocked());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<PermissionEntity> permission) {
        return permission.stream().map(p -> new SimpleGrantedAuthority(p.getPermission().name())).collect(Collectors.toList());
    }
}
