package br.com.lucasmftto.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.lucasmftto.model.entity.Cliente;
import br.com.lucasmftto.model.entity.ServicoPrestado;
import br.com.lucasmftto.model.repository.ClienteRepository;
import br.com.lucasmftto.model.repository.ServicoPrestadoRepository;
import br.com.lucasmftto.rest.dto.ServicoPrestadoDTO;
import br.com.lucasmftto.util.BigDecimalConverter;

@RestController
@RequestMapping("/api/servicos-prestados")
public class ServicoPrestadoController {
	
	@Autowired
	private ClienteRepository clienteRepositoty;
	
	@Autowired
 	private ServicoPrestadoRepository servicoPrestadoRepository;
	
	@Autowired
	private BigDecimalConverter bigDecimalConverter;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {
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
	
	@GetMapping
	public List<ServicoPrestado> pesquisar(
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(value = "mes", required = false) Integer mes){
		
	
		return servicoPrestadoRepository.findByNomeClienteAndMes("%"+nome+"%", mes);
	}
}
