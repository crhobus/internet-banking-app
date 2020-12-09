package br.com.app.person.dto;

import br.com.app.person.model.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class PersonDto implements Serializable {

    private Long id;

    @NotNull(message = "Nome da pessoa não pode ser vazio")
    @Length(min = 2, max = 255, message = "Nome da pessoa deve conter entre 2 e 255 caracteres")
    private String name;

    @NotNull(message = "Gênero da pessoa não pode ser vazio")
    private Gender gender;

    @NotNull(message = "CPF da pessoa não pode ser vazio")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "RG da pessoa não pode ser vazio")
    @Pattern(regexp = "\\d{7}", message = "RG da pessoa deve conter 7 dígitos")
    private String rg;

    @NotNull(message = "Data de nascimento da pessoa não pode ser vazia")
    @Past(message = "Data de nascimento da pessoa inválida")
    private LocalDate birthDate;

    @NotNull(message = "Celular da pessoa não pode ser vazio")
    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", message = "Celular da pessoa inválido")
    private String phone;

    @NotNull(message = "Endereço da pessoa não pode ser vazio")
    @Length(min = 2, max = 255, message = "Endereço da pessoa deve conter entre 2 e 255 caracteres")
    private String address;

    @NotNull(message = "Bairro da pessoa não pode ser vazio")
    @Length(min = 2, max = 180, message = "Bairro da pessoa deve conter entre 2 e 180 caracteres")
    private String neighborhood;

    @NotNull(message = "Número do endereço da pessoa não pode ser vazio")
    @Range(min = 1, max = 999999, message = "Número do endereço da pessoa deve ser entre 1 e 999999")
    private Long number;

    @NotNull(message = "Cidade da pessoa não pode ser vazio")
    @Length(min = 2, max = 200, message = "Cidade da pessoa deve conter entre 2 e 200 caracteres")
    private String city;

    @NotNull(message = "Estado da pessoa não pode ser vazio")
    @Length(min = 2, max = 150, message = "Estado da pessoa deve conter entre 2 e 150 caracteres")
    private String state;

    @NotNull(message = "Nome do usuário da pessoa não pode ser vazio")
    @Length(min = 5, max = 150, message = "Nome do usuário da pessoa deve conter entre 5 e 150 caracteres")
    private String username;

    @NotNull(message = "Senha da pessoa não pode ser vazio")
    @Length(min = 6, max = 255, message = "Senha da pessoa deve conter entre 6 e 255 caracteres")
    private String password;

}
