package com.egt.challenge.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionMessage {
    private HttpStatus status;

    private String message;
 
    public ExceptionMessage(HttpStatus status) {
        this.status = status;
    }

    public ExceptionMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}