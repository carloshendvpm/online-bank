package com.di2win.bancodigital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.di2win.bancodigital.model.Cliente;
import com.di2win.bancodigital.model.Conta;
import com.di2win.bancodigital.repository.ClienteRepository;
import com.di2win.bancodigital.repository.ContaRepository;

class ContaServiceTest {

    @InjectMocks
    ContaService contaService;

    @Mock
    ContaRepository contaRepository;

    @Mock
    ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");

        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setLimiteDiario(BigDecimal.valueOf(1000));
        conta.setSaldo(BigDecimal.ZERO);

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        Conta novaConta = contaService.create("12345678901", BigDecimal.valueOf(1000));

        assertEquals(novaConta.getCliente().getCpf(), cliente.getCpf());
        verify(clienteRepository).findByCpf(anyString());
        verify(contaRepository).save(any(Conta.class));
    }
}