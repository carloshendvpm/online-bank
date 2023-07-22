package com.di2win.bancodigital.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.enums.TipoTransacao;
import com.di2win.bancodigital.model.Transacao;
import com.di2win.bancodigital.repository.TransacaoRepository;

@Service
public class TransacaoService implements ITransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  public List<Transacao> getExtrato(Long contaId) {
      return transacaoRepository.findByContaId(contaId);
  }

  public List<Transacao> getExtratoPorPeriodo(Long contaId, LocalDate inicio, LocalDate fim) {
    LocalDateTime inicioDoDia = inicio.atStartOfDay();
    LocalDateTime fimDoDia = fim.atTime(23, 59, 59);
    return transacaoRepository.findByContaIdAndDataHoraBetween(contaId, inicioDoDia, fimDoDia);
}
  public BigDecimal getTotalSaquesDoDia(Long contaId) {
      LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
      LocalDateTime fimDoDia = LocalDate.now().atTime(LocalTime.MAX);
      List<Transacao> saquesDoDia = transacaoRepository.findByContaIdAndTipoAndDataHoraBetween(contaId, TipoTransacao.SAQUE, inicioDoDia, fimDoDia);
      return saquesDoDia.stream()
        .map(Transacao::getValor)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}