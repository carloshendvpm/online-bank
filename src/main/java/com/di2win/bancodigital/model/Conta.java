package com.di2win.bancodigital.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Conta {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true, name = "numero_agencia")
  private String numero;

  @Column(nullable = false, length = 4, name = "agencia")
  private String agencia;
  @Column(name = "saldo")
  private BigDecimal saldo;
  @Column(nullable = false, name = "bloqueada")
  private boolean bloqueada;
  @Column(nullable = false, name = "limite_diario")
  private BigDecimal limiteDiario;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  @JsonBackReference
  private Cliente cliente;
}