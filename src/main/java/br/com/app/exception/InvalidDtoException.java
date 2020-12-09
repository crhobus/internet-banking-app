package br.com.app.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidDtoException extends RuntimeException {

    private final String message;
    private final List<String> messages;

    public InvalidDtoException(String message, List<String> messages) {
        super(message);
        this.message = message;
        this.messages = messages;
    }

}
