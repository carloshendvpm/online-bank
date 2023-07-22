package com.di2win.bancodigital.exception;

public class ClienteNotFoundException extends RuntimeException {
  public ClienteNotFoundException(String message) {
    super(message);
  }
}
