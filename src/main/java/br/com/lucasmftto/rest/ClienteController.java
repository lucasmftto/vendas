package br.com.lucasmftto.rest;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.lucasmftto.model.entity.Cliente;
import br.com.lucasmftto.model.repository.ClienteRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;
	
	@Value("${security.jwt.signing-key}")
	private String signingKey;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente, @RequestHeader("Authorization") String token) {
		
		String newToken = token.substring(7, token.length());
		 Claims claims2 = null;
		  try {
		    claims2 = Jwts.parser()
		        .setSigningKey(signingKey.getBytes(Charset.forName("UTF-8")))
		        .parseClaimsJws(newToken)
		        .getBody();
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		
		  final List<GrantedAuthority> authorities =
				  Arrays.stream(claims2.get("authorities").toString().split(","))
                  .map(SimpleGrantedAuthority::new)
                  .collect(Collectors.toList());
		  
		  for (GrantedAuthority grantedAuthority : authorities) {
			  System.out.println(grantedAuthority.getAuthority());
		}	 
		return repository.save(cliente);
	}

	@GetMapping("{id}")
	public Cliente findById(@PathVariable Integer id) {
		return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@GetMapping
	public List<Cliente> findAll(){
		return repository.findAll();
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		repository.findById(id)
			.map( cliente -> {
				repository.delete(cliente);
				return Void.TYPE;
			})
			
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @Valid @RequestBody Cliente newCliente) {
		repository.findById(id)
		.map( cliente -> {
			newCliente.setId(cliente.getId());
			repository.save(newCliente);
			return Void.TYPE;
		})
		
	.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

}
