package com.di2win.bancodigital.services;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.models.Cliente;
import com.di2win.bancodigital.models.Conta;
import com.di2win.bancodigital.repositories.ClienteRepository;
import com.di2win.bancodigital.repositories.ContaRepository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private final Random random = new Random();


    public Conta createConta(Conta conta, String cpf) {
      Cliente cliente = clienteRepository.findByCpf(cpf);
      if (cliente == null) {
          throw new IllegalArgumentException("Não foi encontrado um cliente com o CPF fornecido.");
      }
      String numeroConta = gerarNumeroUnico(7);
      String agencia = gerarNumeroUnico(4);

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
          int digito = random.nextInt(10);  // Gera um número aleatório entre 0 e 9
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
        return contaRepository.save(conta);
    }

    public Conta sacar(Long id, BigDecimal valor) {
        Conta conta = findConta(id);
        if (conta.isBloqueada()) {
            throw new IllegalArgumentException("Conta está bloqueada.");
        }
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaRepository.save(conta);
    }

    public void bloquearConta(Long id) {
      Conta conta = findConta(id);
      conta.setBloqueada(true);
      contaRepository.save(conta);
    }
}