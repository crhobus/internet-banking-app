package br.com.app.controller;


import br.com.app.security.AccountCredentialsDto;
import br.com.app.security.jwt.JwtTokenProvider;
import br.com.app.user.UserService;
import br.com.app.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService service;

    @PostMapping(value = "/signin", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity signin(@RequestBody AccountCredentialsDto data) {
        String username = data.getUsername();
        String password = data.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserEntity user = service.findByUsername(username);
            String token = "";
            if (user != null) {
                service.loginSuccess(user);
                token = tokenProvider.createToken(username, user.getPermissions().stream().map(p -> p.getPermission().name()).collect(Collectors.toList()));
            } else {
                throw new UsernameNotFoundException("Usuário " + username + " não encontrado");
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException ex) {
            service.loginAttempts(username);
            throw new BadCredentialsException("Usuário/Senha inválido");
        }
    }

}
