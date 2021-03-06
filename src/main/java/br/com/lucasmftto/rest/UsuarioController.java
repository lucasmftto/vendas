package br.com.lucasmftto.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.lucasmftto.Exceptions.UsuarioCadastradoException;
import br.com.lucasmftto.model.entity.Usuario;
import br.com.lucasmftto.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvar(@RequestBody @Valid Usuario usuario) {
		try {
			usuarioService.salvar(usuario);
		} catch (UsuarioCadastradoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
