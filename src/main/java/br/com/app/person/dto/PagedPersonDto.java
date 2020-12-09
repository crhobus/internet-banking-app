package br.com.app.person.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@JsonPropertyOrder({"found", "isMoreItemsAvailable", "nextPage", "people"})
public class PagedPersonDto implements Serializable {

    private long found;

    @JsonProperty("isMoreItemsAvailable")
    private boolean isMoreItemsAvailable;

    private long nextPage;

    private List<PersonDto> people;
}
