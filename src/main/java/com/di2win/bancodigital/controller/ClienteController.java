package com.di2win.bancodigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.di2win.bancodigital.dtos.ClienteDTO;
import com.di2win.bancodigital.model.Cliente;
import com.di2win.bancodigital.service.IClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
  private IClienteService clienteService;

  @PostMapping
  public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteDTO clienteDTO) {
    Cliente novoCliente = new Cliente(clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getDataNascimento());
    Cliente cliente = clienteService.create(novoCliente);
    return ResponseEntity.ok(cliente);
  }
  @GetMapping
  public List<Cliente> getAll() {
    return clienteService.getAll();
  }
}