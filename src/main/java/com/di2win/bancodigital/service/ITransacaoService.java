package com.di2win.bancodigital.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.di2win.bancodigital.model.Transacao;

public interface ITransacaoService {
    List<Transacao> getExtrato(Long contaId);
    List<Transacao> getExtratoPorPeriodo(Long contaId, LocalDate inicio, LocalDate fim);
    BigDecimal getTotalSaquesDoDia(Long contaId);
}