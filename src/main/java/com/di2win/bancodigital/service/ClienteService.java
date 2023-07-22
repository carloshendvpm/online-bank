package com.di2win.bancodigital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.model.Cliente;
import com.di2win.bancodigital.repository.ClienteRepository;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente create(Cliente cliente) {
        if (!cpfValido(cliente.getCpf()) || clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("CPF inválido ou já existente na base de dados.");
        }
        return clienteRepository.save(cliente);
    }

    private boolean cpfValido(String cpf) {
        // Implementar validação do CPF
        return true;
    }

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }
}