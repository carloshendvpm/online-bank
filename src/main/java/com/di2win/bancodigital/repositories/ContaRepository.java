package com.di2win.bancodigital.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.di2win.bancodigital.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
  Optional<Conta> findByNumero(String numero);
  boolean existsByNumero(String numero);
}
