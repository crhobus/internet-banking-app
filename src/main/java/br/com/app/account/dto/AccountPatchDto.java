package br.com.app.account.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class AccountPatchDto implements Serializable {

    @NotNull(message = "Dígito da conta não pode ser vazio")
    @Range(min = 1, max = 9, message = "Dígito da conta deve ser entre 1 e 9")
    private Integer digit;

    @NotNull(message = "Token de segurança da conta não pode ser vazio")
    @Length(min = 5, max = 255, message = "Token de segurança da conta deve conter entre 5 e 255 caracteres")
    private String securityToken;
}
