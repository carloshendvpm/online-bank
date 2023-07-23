package com.di2win.bancodigital.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ClienteDTO {
    private String nome;
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
}
