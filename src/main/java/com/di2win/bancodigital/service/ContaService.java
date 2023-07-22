package com.di2win.bancodigital.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.enums.TipoTransacao;
import com.di2win.bancodigital.exceptions.SaldoInsuficienteException;
import com.di2win.bancodigital.model.Cliente;
import com.di2win.bancodigital.model.Conta;
import com.di2win.bancodigital.model.Transacao;
import com.di2win.bancodigital.repository.ClienteRepository;
import com.di2win.bancodigital.repository.ContaRepository;
import com.di2win.bancodigital.repository.TransacaoRepository;

@Service
public class ContaService {

  @Autowired
  private ContaRepository contaRepository;
  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private TransacaoRepository transacaoRepository;

  private final Random random = new Random();

  public Conta createConta(Conta conta, String cpf, BigDecimal limiteDiario) {
    Cliente cliente = clienteRepository.findByCpf(cpf);
    if (cliente == null) {
      throw new IllegalArgumentException("Não foi encontrado um cliente com o CPF fornecido.");
    }
    String numeroConta = gerarNumeroUnico(7);
    String agencia = gerarNumeroUnico(4);
    if (limiteDiario == null) {
      limiteDiario = BigDecimal.valueOf(1000);
    }
    conta.setLimiteDiario(limiteDiario);
    conta.setSaldo(BigDecimal.ZERO);
    conta.setNumero(numeroConta);
    conta.setAgencia(agencia);
    conta.setCliente(cliente);
    return contaRepository.save(conta);
  }

  private String gerarNumeroUnico(int digitos) {
    String numero;
    do {
      numero = gerarNumero(digitos);
    } while (contaRepository.existsByNumero(numero));
    return numero;
  }

  private String gerarNumero(int digitos) {
    StringBuilder numero = new StringBuilder(digitos);
    for (int i = 0; i < digitos; i++) {
      int digito = random.nextInt(10);
      numero.append(digito);
    }
    return numero.toString();
  }

  public Conta findConta(Long id) {
    return contaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
  }

  public Conta depositar(Long id, BigDecimal valor) {
    Conta conta = findConta(id);
    if (conta.isBloqueada()) {
      throw new IllegalArgumentException("Conta está bloqueada.");
    }

    conta.setSaldo(conta.getSaldo().add(valor));
    Conta contaAtualizada = contaRepository.save(conta);

    Transacao transacao = new Transacao();
    transacao.setConta(contaAtualizada);
    transacao.setValor(valor);
    transacao.setTipo(TipoTransacao.DEPOSITO);
    transacao.setDataHora(LocalDateTime.now());

    transacaoRepository.save(transacao);

    return contaAtualizada;
  }

  public Conta sacar(Long id, BigDecimal valor) {
    Conta conta = findConta(id);
    if (conta.isBloqueada()) {
      throw new IllegalArgumentException("Conta está bloqueada.");
    }
    if (conta.getSaldo().compareTo(valor) < 0) {
      throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
    }
    LocalDate hoje = LocalDate.now();
    BigDecimal totalSaquesHoje = transacaoRepository
        .findByContaIdAndTipoAndDataHoraBetween(id, TipoTransacao.SAQUE, hoje.atStartOfDay(),
            hoje.plusDays(1).atStartOfDay())
        .stream()
        .map(Transacao::getValor)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (totalSaquesHoje.add(valor).compareTo(conta.getLimiteDiario()) > 0) {
      throw new IllegalArgumentException("Limite diário excedido para saques.");
    }
    conta.setSaldo(conta.getSaldo().subtract(valor));
    Conta contaAtualizada = contaRepository.save(conta);

    Transacao transacao = new Transacao();
    transacao.setConta(contaAtualizada);
    transacao.setValor(valor);
    transacao.setTipo(TipoTransacao.SAQUE);
    transacao.setDataHora(LocalDateTime.now());

    transacaoRepository.save(transacao);

    return contaAtualizada;
  }

  public void bloquearConta(Long id) {
    Conta conta = findConta(id);
    conta.setBloqueada(true);
    contaRepository.save(conta);
  }
}