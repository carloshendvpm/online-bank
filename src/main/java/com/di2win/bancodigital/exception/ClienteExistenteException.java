package com.di2win.bancodigital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException(String message) {
      super(message);
    }
}