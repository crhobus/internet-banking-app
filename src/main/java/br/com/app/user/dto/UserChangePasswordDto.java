package br.com.app.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class UserChangePasswordDto implements Serializable {

    @NotNull(message = "Senha do usuário não pode ser vazio")
    @Length(min = 6, max = 255, message = "Senha do usuário deve conter entre 6 e 255 caracteres")
    private String password;

    @NotNull(message = "Nova senha do usuário não pode ser vazio")
    @Length(min = 6, max = 255, message = "Nova senha do usuário deve conter entre 6 e 255 caracteres")
    private String newPassword;

}
