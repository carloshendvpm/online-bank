package com.di2win.bancodigital.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
      super(message);
    }
}