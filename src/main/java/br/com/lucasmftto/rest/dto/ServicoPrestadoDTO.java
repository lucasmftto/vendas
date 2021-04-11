package br.com.lucasmftto.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicoPrestadoDTO {
	
	@NotEmpty(message = "{descricao.obrigatorio}")
	private String descricao;
	
	@NotEmpty(message = "{preco.obrigatorio}")
	private String preco;
	
	@NotEmpty(message = "{data.obrigatorio}")
	private String data;
	
	@NotNull(message = "{cliente.obrigatorio}")
	private Integer idCliente; 
}
