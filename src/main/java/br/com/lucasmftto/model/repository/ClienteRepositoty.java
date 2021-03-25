package br.com.lucasmftto.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasmftto.model.entity.Cliente;

public interface ClienteRepositoty extends JpaRepository<Cliente, Integer> {

}
