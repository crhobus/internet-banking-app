package br.com.app.user.model;

import br.com.app.infra.database.EntityBase;
import br.com.app.permission.model.PermissionEntity;
import br.com.app.person.model.PersonEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "user_account")
@SequenceGenerator(name = "SEQUENCE_BASE", sequenceName = "user_account_seq", allocationSize = 1)
public class UserEntity extends EntityBase {

    @Column(name = "user_name", length = 150, unique = true, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "locked", nullable = false)
    private boolean locked;

    @Column(name = "login_attempts", nullable = false)
    private int loginAttempts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = {@JoinColumn(name = "id_user_account")}, inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private List<PermissionEntity> permissions;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private PersonEntity person;

}
