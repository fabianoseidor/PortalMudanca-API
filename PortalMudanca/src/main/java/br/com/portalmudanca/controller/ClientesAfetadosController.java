package br.com.portalmudanca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.ClientesAfetados;
import br.com.portalmudanca.service.ClientesAfetadosService;

@RestController
public class ClientesAfetadosController {

	@Autowired
	private ClientesAfetadosService clientesAfetadosService;
	
	@ResponseBody // Retorno da API 
	@PostMapping("**/salvarClientesAfetados") // Mepeando a url para receber um JSON
	public ResponseEntity< ClientesAfetados> salvarClientesAfetados( @RequestBody ClientesAfetados clientesAfetado ) throws ExceptionCustomizada {// @RequestBody ==> Recebe um JSON e converte para Objeto. 
		
		if(clientesAfetado == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar os Clientes Afetados em uma GMUD. Valores vazios!");
		}

		clientesAfetado = clientesAfetadosService.save(clientesAfetado);
		
		return new ResponseEntity<ClientesAfetados>( clientesAfetado, HttpStatus.OK );
	}
}
