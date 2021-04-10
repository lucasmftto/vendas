package br.com.lucasmftto.rest;

import java.lang.StackWalker.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.lucasmftto.model.entity.Cliente;
import br.com.lucasmftto.model.entity.ServicoPrestado;
import br.com.lucasmftto.model.repository.ClienteRepositoty;
import br.com.lucasmftto.model.repository.ServicoPrestadoRepository;
import br.com.lucasmftto.rest.dto.ServicoPrestadoDTO;
import br.com.lucasmftto.util.BigDecimalConverter;

@RestController
@RequestMapping("/api/servicos-prestados")
public class ServicoPrestadoContrller {
	
	@Autowired
	private ClienteRepositoty clienteRepositoty;
	
	@Autowired
 	private ServicoPrestadoRepository servicoPrestadoRepository;
	
	@Autowired
	private BigDecimalConverter bigDecimalConverter;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody ServicoPrestadoDTO dto) {
		ServicoPrestado servicoPrestado = new ServicoPrestado();
		LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		Cliente cliente = 
				clienteRepositoty
					.findById(dto.getIdCliente())
					.orElseThrow(() -> 
						new ResponseStatusException(
								HttpStatus.BAD_REQUEST, "Cliente inexistente."));
		
		servicoPrestado.setDescricao(dto.getDescricao());
		servicoPrestado.setData(data);
		servicoPrestado.setCliente(cliente);
		servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));
		
		return servicoPrestadoRepository.save(servicoPrestado);
	}
}
