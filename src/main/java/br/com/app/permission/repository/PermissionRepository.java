package br.com.app.permission.repository;

import br.com.app.infra.database.RepositoryBase;
import br.com.app.permission.model.Permission;
import br.com.app.permission.model.PermissionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends RepositoryBase<PermissionEntity> {

    PermissionEntity findByPermission(Permission permission);

    List<PermissionEntity> findByPermissionIn(List<Permission> permissions);
}
