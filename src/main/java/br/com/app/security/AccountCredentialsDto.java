package br.com.app.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class AccountCredentialsDto implements Serializable {

    private String username;

    private String password;
}
