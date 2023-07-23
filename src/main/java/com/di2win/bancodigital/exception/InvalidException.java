package com.di2win.bancodigital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidException extends RuntimeException {
  public InvalidException(String message){
    super(message);
  }
}
