package br.com.app.bank.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class BankDto implements Serializable {

    private Long id;

    @NotNull(message = "Nome do banco não pode ser vazio")
    @Length(min = 2, max = 255, message = "Nome do banco deve conter entre 2 e 255 caracteres")
    private String name;
}
