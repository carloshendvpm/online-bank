package com.di2win.bancodigital.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExtratoDTO {
  private LocalDate dataInicio;
  private LocalDate dataFim;
}
