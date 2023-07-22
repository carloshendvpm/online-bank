package com.di2win.bancodigital.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Conta {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String numero;

  @Column(nullable = false, unique = true)
  private String agencia;
  private BigDecimal saldo;
  private boolean bloqueada;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  @JsonBackReference
  private Cliente cliente;

  public Conta(String numero, String agencia, Cliente cliente) {
    this.numero = numero;
    this.agencia = agencia;
    this.cliente = cliente;
    this.saldo = BigDecimal.ZERO;
    this.bloqueada = false;
}
}