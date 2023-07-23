package com.di2win.bancodigital.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.enums.TipoTransacao;
import com.di2win.bancodigital.exception.NotFoundException;
import com.di2win.bancodigital.exception.SaldoInsuficienteException;
import com.di2win.bancodigital.model.Cliente;
import com.di2win.bancodigital.model.Conta;
import com.di2win.bancodigital.model.Transacao;
import com.di2win.bancodigital.repository.ClienteRepository;
import com.di2win.bancodigital.repository.ContaRepository;
import com.di2win.bancodigital.repository.TransacaoRepository;

@Service
public class ContaService implements IContaService {

  private static final BigDecimal LIMITE_DIARIO_DEFAULT = BigDecimal.valueOf(2000);
  private static final int NUMERO_CONTA_TAMANHO = 7;
  private static final int NUMERO_AGENCIA_TAMANHO = 4;

  @Autowired
  private ContaRepository contaRepository;
  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private TransacaoRepository transacaoRepository;

  private final Random random = new Random();

  @Override
  public List<Conta> getAllContas() {
    return contaRepository.findAll();
  }
  

  @Override
  public Conta create(String cpf, BigDecimal limiteDiario) {
    Cliente cliente = obterCliente(cpf);
    String numeroConta = gerarNumeroUnico(NUMERO_CONTA_TAMANHO);
    String agencia = gerarNumeroUnico(NUMERO_AGENCIA_TAMANHO);
    limiteDiario = limiteDiario != null ? limiteDiario : LIMITE_DIARIO_DEFAULT;
    Conta novaConta = construirConta(cliente, numeroConta, agencia, limiteDiario);

    return contaRepository.save(novaConta);
  }

  private Cliente obterCliente(String cpf) {
    Cliente cliente = clienteRepository.findByCpf(cpf);
    if (cliente == null) {
      throw new NotFoundException("Não foi encontrado um cliente com o CPF fornecido.");
    }
    return cliente;
  }

  private Conta construirConta(Cliente cliente, String numeroConta, String agencia, BigDecimal limiteDiario) {
    return Conta.builder()
                .limiteDiario(limiteDiario)
                .saldo(BigDecimal.ZERO)
                .numero(numeroConta)
                .agencia(agencia)
                .cliente(cliente)
                .build();
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

  @Override
  public Conta find(Long id) {
    return contaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
  }

  @Override
  public Conta depositar(Long id, BigDecimal valor) {
    Conta conta = find(id);
    if (conta.isBloqueada()) {
      throw new IllegalArgumentException("Conta está bloqueada.");
    }

    conta.setSaldo(conta.getSaldo().add(valor));
    Conta contaAtualizada = contaRepository.save(conta);

    Transacao transacao = Transacao.builder()
                            .conta(contaAtualizada)
                            .valor(valor)
                            .tipo(TipoTransacao.DEPOSITO)
                            .dataHora(LocalDateTime.now())
                            .build();

    transacaoRepository.save(transacao);

    return contaAtualizada;
  }

  @Override
  public Conta sacar(Long id, BigDecimal valor) {
    Conta conta = find(id);
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

    Transacao transacao = Transacao.builder()
                            .conta(contaAtualizada)
                            .valor(valor)
                            .tipo(TipoTransacao.SAQUE)
                            .dataHora(LocalDateTime.now())
                            .build();

    transacaoRepository.save(transacao);

    return contaAtualizada;
  }

  @Override
  public void bloquear(Long id) {
    Conta conta = find(id);
    conta.setBloqueada(true);
    contaRepository.save(conta);
  }
}