package com.di2win.bancodigital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.di2win.bancodigital.exception.ClienteExistenteException;
import com.di2win.bancodigital.exception.InvalidException;
import com.di2win.bancodigital.model.Cliente;
import com.di2win.bancodigital.repository.ClienteRepository;
import com.di2win.bancodigital.utils.CpfValidator;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente create(Cliente cliente) {
        String cpf = cliente.getCpf() != null ? cliente.getCpf().replaceAll("[.-]", "") : null;
        if (!cpfValido(cpf)) {
            throw new InvalidException("CPF inválido.");
        }   
        if (clienteRepository.findByCpf(cpf) != null) {
            throw new ClienteExistenteException("CPF já existente na base de dados.");
        }
        return clienteRepository.save(cliente);
    }

    private boolean cpfValido(String cpf) {
        return cpf != null && CpfValidator.isCPF(cpf);
    }
    
    @Override
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }
}