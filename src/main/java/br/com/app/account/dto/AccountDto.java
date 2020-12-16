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
public class AccountDto implements Serializable {

    private Long id;

    @NotNull(message = "Agência não pode ser vazia")
    @Length(min = 1, max = 10, message = "Agência deve conter entre 1 e 10 dígitos")
    private String agency;

    @NotNull(message = "Número da conta não pode ser vazia")
    @Length(min = 5, max = 40, message = "Número da conta deve conter entre 5 e 40 dígitos")
    private String accountNumber;

    @NotNull(message = "Dígito da conta não pode ser vazio")
    @Range(min = 1, max = 9, message = "Dígito da conta deve ser entre 1 e 9")
    private Integer digit;

    @NotNull(message = "Token de segurança da conta não pode ser vazio")
    @Length(min = 5, max = 255, message = "Token de segurança da conta deve conter entre 5 e 255 caracteres")
    private String securityToken;

    @NotNull(message = "Id do banco não pode ser vazio")
    private Long bankId;

    @NotNull(message = "Id da pessoa não pode ser vazia")
    private Long personId;
}
