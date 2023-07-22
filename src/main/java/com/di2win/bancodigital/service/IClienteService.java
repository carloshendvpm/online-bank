package com.di2win.bancodigital.service;
import java.util.List;

import com.di2win.bancodigital.model.Cliente;

public interface IClienteService {

  Cliente create(Cliente cliente);

  List<Cliente> getAll();

}