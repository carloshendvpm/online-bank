package com.di2win.bancodigital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ContaBloqueadaException extends RuntimeException {
    public ContaBloqueadaException(String message) {
      super(message);
    }
}