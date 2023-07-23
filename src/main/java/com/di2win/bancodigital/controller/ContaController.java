package com.di2win.bancodigital.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
import com.di2win.bancodigital.model.Conta;
import com.di2win.bancodigital.model.Transacao;
import com.di2win.bancodigital.service.IContaService;
import com.di2win.bancodigital.service.ITransacaoService;

@RestController
@RequestMapping("/contas")
public class ContaController {

  @Autowired
  private IContaService contaService;
  @Autowired
  private ITransacaoService transacaoService;

  @GetMapping
  public ResponseEntity<List<ContaDTO>> getAllContas() {
    List<Conta> contas = contaService.getAllContas();
    List<ContaDTO> contaDTOs = contas.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(contaDTOs);
  }

  @PostMapping
  public ResponseEntity<ContaDTO> create(@RequestBody ContaDTO contaDTO) {
    Conta conta = contaService.create(contaDTO.getCpf(), contaDTO.getLimiteDiario());
    ContaDTO createdContaDTO = convertToDTO(conta);
    return ResponseEntity.ok(createdContaDTO);
  }

  private ContaDTO convertToDTO(Conta conta) {
    ContaDTO dto = new ContaDTO();
    dto.setId(conta.getId());
    dto.setCpf(conta.getCliente().getCpf());
    dto.setLimiteDiario(conta.getLimiteDiario());
    dto.setSaldo(conta.getSaldo());
    dto.setNomeDono(conta.getCliente().getNome());
    return dto;
  }

  @GetMapping("/{id}/saldo")
  public BigDecimal getSaldo(@PathVariable Long id) {
    return contaService.find(id).getSaldo();
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
  public ResponseEntity<Void> bloquear(@PathVariable Long id) {
    contaService.bloquear(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/extrato")
  public List<Transacao> getExtrato(@PathVariable Long id) {
    return transacaoService.getExtrato(id);
  }

  @GetMapping("/{id}/extratoPorPeriodo")
  public List<Transacao> getExtratoPorPeriodo(@PathVariable Long id,
      @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate inicio,
      @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fim) {
    return transacaoService.getExtratoPorPeriodo(id, inicio, fim);
  }
}