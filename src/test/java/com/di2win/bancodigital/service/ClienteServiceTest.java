package com.di2win.bancodigital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.di2win.bancodigital.exception.ClienteExistenteException;
import com.di2win.bancodigital.exception.InvalidException;
import com.di2win.bancodigital.model.Cliente;
import com.di2win.bancodigital.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

  @InjectMocks
  private ClienteService clienteService;

  @Mock
  private ClienteRepository clienteRepository;

  @Test
  public void testCreateNewCliente() {
    Cliente cliente = new Cliente();
    cliente.setCpf("529.982.247-25"); // CPF válido para teste

    when(clienteRepository.findByCpf(any(String.class))).thenReturn(null);
    when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

    Cliente savedCliente = clienteService.create(cliente);

    assertNotNull(savedCliente);
    verify(clienteRepository, times(1)).findByCpf(any(String.class));
    verify(clienteRepository, times(1)).save(any(Cliente.class));
  }

  @Test
  public void testGetAllClientes() {
    Cliente cliente = new Cliente();
    cliente.setCpf("529.982.247-25"); // CPF válido para teste

    when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));

    List<Cliente> clientes = clienteService.getAll();

    assertFalse(clientes.isEmpty());
    verify(clienteRepository, times(1)).findAll();
  }

  @Test
  public void testCreateClienteWithInvalidCPF() {
    Cliente cliente = new Cliente();
    cliente.setCpf("123.456.789-10"); // CPF inválido para teste

    assertThrows(InvalidException.class, () -> {
      clienteService.create(cliente);
    });
  }

  @Test
  public void testCreateClienteWithExistingCPF() {
    Cliente cliente = new Cliente();
    cliente.setCpf("529.982.247-25"); // Um CPF válido para teste

    when(clienteRepository.findByCpf(any(String.class))).thenReturn(cliente);

    assertThrows(ClienteExistenteException.class, () -> {
      clienteService.create(cliente);
    });
  }

  @Test
  public void testCreateClienteWithNullCPF() {
    Cliente cliente = new Cliente();
    cliente.setCpf(null);

    assertThrows(InvalidException.class, () -> {
      clienteService.create(cliente);
    });
  }

  @Test
  public void testCreateClienteWithEmptyCPF() {
    Cliente cliente = new Cliente();
    cliente.setCpf("");

    assertThrows(InvalidException.class, () -> {
      clienteService.create(cliente);
    });
  }

  @Test
  public void testCreateClienteWithNonNumericCPF() {
    Cliente cliente = new Cliente();
    cliente.setCpf("abc.def.ghi-jk");

    assertThrows(InvalidException.class, () -> {
      clienteService.create(cliente);
    });
  }

  @Test
  public void testValidCPFFormat() {
    String cpf = "529.982.247-25";
    String formattedCpf = cpf.replaceAll("[.-]", "");
    assertEquals("52998224725", formattedCpf);
  }

  @Test
  public void testCreateClientRepositoryError() {
    Cliente cliente = new Cliente();
    cliente.setCpf("529.982.247-25"); // Um CPF válido para teste

    when(clienteRepository.findByCpf(any(String.class))).thenReturn(null);
    when(clienteRepository.save(any(Cliente.class))).thenThrow(new RuntimeException("Error during save"));

    assertThrows(RuntimeException.class, () -> {
      clienteService.create(cliente);
    });
  }

}