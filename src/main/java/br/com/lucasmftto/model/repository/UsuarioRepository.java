package br.com.lucasmftto.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasmftto.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
