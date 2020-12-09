package br.com.app.person.dto;

import br.com.app.permission.model.Permission;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class PersonPermissionsDto implements Serializable {

    private List<Permission> permissions;
}
