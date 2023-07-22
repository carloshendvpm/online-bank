package com.di2win.bancodigital.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
      super(message);
    }
}