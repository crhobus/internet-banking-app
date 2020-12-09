package br.com.app.user.repository;

import br.com.app.infra.database.RepositoryBase;
import br.com.app.user.model.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RepositoryBase<UserEntity> {

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.enabled = false WHERE u.id = :id")
    void disableUser(@Param("id") long id);

    @Modifying
    @Query("UPDATE UserEntity u SET u.enabled = true, u.locked = false, u.loginAttempts = 0 WHERE u.id = :id")
    void enableUser(@Param("id") long id);
}
