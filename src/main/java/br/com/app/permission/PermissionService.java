package br.com.app.permission;

import br.com.app.permission.model.Permission;
import br.com.app.permission.model.PermissionEntity;
import br.com.app.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Transactional(readOnly = true)
    public PermissionEntity findByPermission(Permission permission) {
        return repository.findByPermission(permission);
    }

    @Transactional(readOnly = true)
    public List<PermissionEntity> findByPermissionIn(List<Permission> permissions) {
        return repository.findByPermissionIn(permissions);
    }
}
