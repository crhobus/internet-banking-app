package br.com.app.controller;

import br.com.app.exception.BadRequestException;
import br.com.app.exception.InvalidDtoException;
import br.com.app.person.PersonService;
import br.com.app.person.dto.PagedPersonDto;
import br.com.app.person.dto.PersonDto;
import br.com.app.person.dto.PersonPermissionsDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api.com/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public PersonDto create(@Valid @RequestBody PersonDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidDtoException("Entrada inválida", errors);
        }
        return service.create(dto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping(produces = {"application/json"}, consumes = {"application/json"})
    public PersonDto update(@Valid @RequestBody PersonDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidDtoException("Entrada inválida", errors);
        }
        return service.update(dto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public PersonDto get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping(produces = {"application/json"})
    public PagedPersonDto getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "100") int size,
                                 @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        if (!StringUtils.equalsAnyIgnoreCase(direction, "asc", "desc")) {
            throw new BadRequestException("Direcionamento da pesquisa inválido");
        }
        return service.getAll(page, size, direction);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PatchMapping(value = "/disable/{id}", produces = {"application/json"})
    public String disablePerson(@PathVariable("id") Long id) {
        return service.disablePerson(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PatchMapping(value = "/enable/{id}", produces = {"application/json"})
    public String enablePerson(@PathVariable("id") Long id) {
        return service.enablePerson(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PatchMapping(value = "/add-permissions/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public String addPermissionsPerson(@PathVariable("id") Long id, @Valid @RequestBody PersonPermissionsDto dto, BindingResult result) {
        return service.addPermissionsPerson(id, dto.getPermissions());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PatchMapping(value = "/remove-permissions/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public String removePermissionsPerson(@PathVariable("id") Long id, @Valid @RequestBody PersonPermissionsDto dto, BindingResult result) {
        return service.removePermissionsPerson(id, dto.getPermissions());
    }

}
