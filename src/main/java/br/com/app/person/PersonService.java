package br.com.app.person;

import br.com.app.exception.BadRequestException;
import br.com.app.exception.ResourceNotFoundException;
import br.com.app.permission.model.Permission;
import br.com.app.person.dto.PagedPersonDto;
import br.com.app.person.dto.PersonDto;
import br.com.app.person.model.PersonEntity;
import br.com.app.person.repository.PersonRepository;
import br.com.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonConverter converter;

    @Transactional
    public PersonDto create(PersonDto dto) {
        if (repository.existsByCpfOrRg(dto.getCpf(), dto.getRg())) {
            throw new BadRequestException("O CPF/RG informado já está cadastrado para outra pessoa");
        }

        var person = converter.toPersonEntity(dto);
        var user = userService.createUser(dto.getUsername(), dto.getPassword());
        person.setUser(user);
        repository.save(person);

        return converter.toPersonDto(person);
    }

    @Transactional
    public PersonDto update(PersonDto dto) {
        var person = repository.findByIdAndCpfAndRgAndUsername(dto.getId(), dto.getCpf(), dto.getRg(), dto.getUsername());
        if (person == null) {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }

        converter.merge(person, dto);
        repository.save(person);

        return converter.toPersonDto(person);
    }

    @Transactional(readOnly = true)
    public PersonDto get(Long id) {
        var person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        return converter.toPersonDto(person);
    }

    @Transactional(readOnly = true)
    public PagedPersonDto getAll(int page, int size, String direction) {
        Direction sortDirection = "asc".equalsIgnoreCase(direction) ? Direction.ASC : Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<PersonEntity> people = repository.findAll(pageable);

        return converter.toPagedPersonDto(people);
    }

    @Transactional
    public String disablePerson(Long id) {
        var person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        userService.disableUser(person.getUser().getId());
        return "Usuário " + person.getCpf() + " - " + person.getName() + " desabilitado do Internet Banking";
    }

    @Transactional
    public String enablePerson(Long id) {
        var person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        userService.enableUser(person.getUser().getId());
        return "Usuário " + person.getCpf() + " - " + person.getName() + " habilitado no Internet Banking";
    }

    @Transactional
    public String addPermissionsPerson(Long id, List<Permission> permissions) {
        var person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        userService.addPermissionUser(person.getUser(), permissions.stream().collect(Collectors.toSet()));
        return "Permissões atribuídas com sucesso ao Internet Banking para o usuário " + person.getCpf() + " - " + person.getName();
    }

    @Transactional
    public String removePermissionsPerson(Long id, List<Permission> permissions) {
        var person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        userService.removePermissionUser(person.getUser(), permissions.stream().collect(Collectors.toSet()));
        return "Permissões removidas com sucesso do Internet Banking para o usuário " + person.getCpf() + " - " + person.getName();
    }

    @Transactional(readOnly = true)
    public PersonEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
