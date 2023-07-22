package com.di2win.bancodigital.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClienteDTO {
  private String nome;
  private String cpf;
  private LocalDate dataNascimento;
}
