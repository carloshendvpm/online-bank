package com.di2win.bancodigital.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.di2win.bancodigital.dtos.ClienteDTO;
import com.di2win.bancodigital.models.Cliente;
import com.di2win.bancodigital.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @PostMapping
  public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteDTO clienteDTO) {
    Cliente novoCliente = new Cliente(clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getDataNascimento());
    Cliente cliente = clienteService.createCliente(novoCliente);
    return ResponseEntity.ok(cliente);
  }
  @GetMapping
  public List<Cliente> getAllClientes() {
    return clienteService.getAllClientes();
  }
}