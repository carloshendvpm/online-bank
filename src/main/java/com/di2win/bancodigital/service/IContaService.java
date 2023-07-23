package com.di2win.bancodigital.service;

import java.math.BigDecimal;
import java.util.List;

import com.di2win.bancodigital.model.Conta;

public interface IContaService {
    Conta create(String cpf, BigDecimal limiteDiario);
    Conta find(Long id);
    Conta depositar(Long id, BigDecimal valor);
    Conta sacar(Long id, BigDecimal valor);
    void bloquear(Long id);
    List<Conta> getAllContas();
}