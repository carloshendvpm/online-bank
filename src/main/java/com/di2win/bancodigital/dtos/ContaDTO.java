package com.di2win.bancodigital.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ContaDTO {
  private String cpf;
  private BigDecimal limiteDiario;
}
