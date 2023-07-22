package com.di2win.bancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.di2win.bancodigital.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Cliente findByCpf(String cpf);
}