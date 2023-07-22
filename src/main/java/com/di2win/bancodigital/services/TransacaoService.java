package com.di2win.bancodigital.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.models.Transacao;
import com.di2win.bancodigital.repositories.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> getExtrato(Long contaId, LocalDate inicio, LocalDate fim) {
      return transacaoRepository.findByContaIdAndDataHoraBetween(contaId, inicio, fim);
    }
}