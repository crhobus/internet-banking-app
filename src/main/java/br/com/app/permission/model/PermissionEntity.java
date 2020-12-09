package br.com.app.permission.model;

import br.com.app.infra.database.EntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "permission")
@SequenceGenerator(name = "SEQUENCE_BASE", sequenceName = "permission_seq", allocationSize = 1)
public class PermissionEntity extends EntityBase {

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", length = 50, unique = true, nullable = false)
    private Permission permission;
}
