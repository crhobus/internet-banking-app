package br.com.app.person;

import br.com.app.person.dto.PagedPersonDto;
import br.com.app.person.dto.PersonDto;
import br.com.app.person.model.PersonEntity;
import br.com.app.util.DozerConverter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PersonConverter {

    public PersonDto toPersonDto(PersonEntity person) {
        PersonDto dto = DozerConverter.parseObject(person, PersonDto.class);
        dto.setUsername(person.getUser().getUsername());
        return dto;
    }

    public PersonEntity toPersonEntity(PersonDto dto) {
        return DozerConverter.parseObject(dto, PersonEntity.class);
    }

    public void merge(PersonEntity entity, PersonDto dto) {
        DozerConverter.parseObject(dto, entity);
    }

    public PagedPersonDto toPagedPersonDto(Page<PersonEntity> people) {
        PagedPersonDto dto = new PagedPersonDto();

        dto.setFound(people.getTotalElements());
        dto.setMoreItemsAvailable(people.hasNext());
        dto.setNextPage(people.hasNext() ? people.getNumber() + 1L : 0L);
        dto.setPeople(people.getContent().stream().map(this::toPersonDto).collect(Collectors.toList()));

        return dto;
    }
}
