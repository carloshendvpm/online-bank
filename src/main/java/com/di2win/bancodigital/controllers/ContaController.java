package com.di2win.bancodigital.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.di2win.bancodigital.dtos.ContaDTO;
import com.di2win.bancodigital.dtos.ValorTransacaoDTO;
import com.di2win.bancodigital.models.Conta;
import com.di2win.bancodigital.models.Transacao;
import com.di2win.bancodigital.services.ContaService;
import com.di2win.bancodigital.services.TransacaoService;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;
    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Conta> createConta(@RequestBody ContaDTO contaDTO) {
      Conta novaConta = new Conta();
      Conta conta = contaService.createConta(novaConta, contaDTO.getCpf());
      return ResponseEntity.ok(conta);
    }


    @GetMapping("/{id}/saldo")
    public BigDecimal getSaldo(@PathVariable Long id) {
        return contaService.findConta(id).getSaldo();
    }

    @PostMapping("/{id}/depositar")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestBody ValorTransacaoDTO valorTransacaoDTO) {
      Conta conta = contaService.depositar(id, valorTransacaoDTO.getValor());
      return ResponseEntity.ok(conta);
    }

    @PostMapping("/{id}/sacar")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestBody ValorTransacaoDTO valorTransacaoDTO) {
      Conta conta = contaService.sacar(id, valorTransacaoDTO.getValor());
      return ResponseEntity.ok(conta);
    }

    @PostMapping("/{id}/bloquear")
    public ResponseEntity<Void> bloquearConta(@PathVariable Long id) {
      contaService.bloquearConta(id);
      return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/extrato")
    public List<Transacao> getExtrato(@PathVariable Long id, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam LocalDate inicio, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam LocalDate fim) {
      return transacaoService.getExtrato(id, inicio, fim);
    }
}