package com.di2win.bancodigital.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.di2win.bancodigital.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaIdAndDataHoraBetween(Long contaId, LocalDate inicio, LocalDate fim);
}
