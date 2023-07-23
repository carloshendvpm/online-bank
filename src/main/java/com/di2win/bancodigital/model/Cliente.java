package com.di2win.bancodigital.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 100, name = "nome")
  private String nome;

  @Column(nullable = false, length = 100, unique = true, name = "cpf")
  @NotBlank(message = "O CPF é obrigatório")
  private String cpf;

  @Column(name = "data_nascimento")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Conta> contas;

  public Cliente(String nome, String cpf, LocalDate dataNascimento) {
    this.nome = nome;
    this.cpf = cpf;
    this.dataNascimento = dataNascimento;
  }

}
