package com.di2win.bancodigital.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.di2win.bancodigital.enums.TipoTransacao;
import com.di2win.bancodigital.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
  List<Transacao> findByContaIdAndDataHoraBetween(Long contaId, LocalDateTime inicio, LocalDateTime fim);
  List<Transacao> findByContaIdAndTipoAndDataHoraBetween(Long contaId, TipoTransacao tipo, LocalDateTime inicio, LocalDateTime fim);
  List<Transacao> findByContaId(Long contaId);
}
