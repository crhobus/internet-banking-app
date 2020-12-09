package br.com.app.controller;

import br.com.app.bank.BankService;
import br.com.app.bank.dto.BankDto;
import br.com.app.bank.dto.PagedBankDto;
import br.com.app.exception.BadRequestException;
import br.com.app.exception.InvalidDtoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api.com/bank")
public class BankController {

    @Autowired
    private BankService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public BankDto create(@Valid @RequestBody BankDto bankDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidDtoException("Entrada inválida", errors);
        }
        return service.create(bankDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping(produces = {"application/json"}, consumes = {"application/json"})
    public BankDto update(@Valid @RequestBody BankDto bankDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidDtoException("Entrada inválida", errors);
        }
        return service.update(bankDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public BankDto findById(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping(produces = {"application/json"})
    public PagedBankDto getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "100") int size,
                               @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        if (!StringUtils.equalsAnyIgnoreCase(direction, "asc", "desc")) {
            throw new BadRequestException("Direcionamento da pesquisa inválido");
        }
        return service.getAll(page, size, direction);
    }

}
