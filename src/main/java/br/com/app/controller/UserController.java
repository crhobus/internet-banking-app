package br.com.app.controller;

import br.com.app.exception.InvalidDtoException;
import br.com.app.user.UserService;
import br.com.app.user.dto.UserChangePasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api.com/user")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @PatchMapping(value = "/change-password/{username}", produces = {"application/json"}, consumes = {"application/json"})
    public String changePassword(@PathVariable("username") String username, @Valid @RequestBody UserChangePasswordDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidDtoException("Entrada inválida", errors);
        }
        return service.changePassword(username, dto);
    }
}
