package com.di2win.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.di2win.bancodigital.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Cliente findByCpf(String cpf);
}