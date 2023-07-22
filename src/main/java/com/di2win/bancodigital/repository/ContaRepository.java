package com.di2win.bancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.di2win.bancodigital.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	Optional<Conta> findByNumero(String numero);

	boolean existsByNumero(String numero);
}
