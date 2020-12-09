package br.com.app.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
    private List<String> messages;
    private String path;
}
