package com.di2win.bancodigital.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ContaDTO {
  private Long id;
  private String cpf;
  private BigDecimal limiteDiario;
  private BigDecimal saldo;
  private String nomeDono;
}
