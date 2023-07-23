package com.di2win.bancodigital.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.enums.TipoTransacao;
import com.di2win.bancodigital.model.Conta;
import com.di2win.bancodigital.model.Transacao;
import com.di2win.bancodigital.repository.TransacaoRepository;

@Service
public class TransacaoService implements ITransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  @Autowired
  private IContaService contaService;

  private void validarConta(Long contaId) {
    Conta conta = contaService.find(contaId);
    if (conta.isBloqueada()) {
      throw new IllegalArgumentException("A conta está bloqueada e não pode consultar o extrato.");
    }
  }

  @Override
  public List<Transacao> getExtrato(Long contaId) {
      validarConta(contaId);
      return transacaoRepository.findByContaId(contaId);
  }

  @Override
  public List<Transacao> getExtratoPorPeriodo(Long contaId, LocalDate inicio, LocalDate fim) {
    validarConta(contaId);
    LocalDateTime inicioDoDia = inicio.atStartOfDay();
    LocalDateTime fimDoDia = fim.atTime(23, 59, 59);
    return transacaoRepository.findByContaIdAndDataHoraBetween(contaId, inicioDoDia, fimDoDia);
  } 

  @Override
  public BigDecimal getTotalSaquesDoDia(Long contaId) {
      validarConta(contaId);
      LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
      LocalDateTime fimDoDia = LocalDate.now().atTime(LocalTime.MAX);
      List<Transacao> saquesDoDia = transacaoRepository.findByContaIdAndTipoAndDataHoraBetween(contaId, TipoTransacao.SAQUE, inicioDoDia, fimDoDia);
      return saquesDoDia.stream()
        .map(Transacao::getValor)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}