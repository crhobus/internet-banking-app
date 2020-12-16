package br.com.app.controller;

import br.com.app.account.AccountService;
import br.com.app.account.dto.AccountDto;
import br.com.app.account.dto.AccountPatchDto;
import br.com.app.exception.InvalidDtoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api.com/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public AccountDto create(@Valid @RequestBody AccountDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidDtoException("Entrada inválida", errors);
        }
        return service.create(dto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PatchMapping(value = "/patch/{accountNumber}", produces = {"application/json"}, consumes = {"application/json"})
    public Boolean patch(@PathVariable("accountNumber") String accountNumber, @Valid @RequestBody AccountPatchDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidDtoException("Entrada inválida", errors);
        }
        return service.patch(accountNumber, dto);
    }
}
