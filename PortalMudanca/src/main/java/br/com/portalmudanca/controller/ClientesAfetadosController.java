package br.com.portalmudanca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.portalmudanca.model.ClientesAfetados;
import br.com.portalmudanca.service.ClientesAfetadosService;

@Controller
public class ClientesAfetadosController {

	@Autowired
	private ClientesAfetadosService clientesAfetadosService;
	
	@ResponseBody // Retorno da API 
	@PostMapping("/salvarClientesAfetados") // Mepeando a url para receber um JSON
	public ResponseEntity< ClientesAfetados> salvarClientesAfetados( @RequestBody ClientesAfetados clientesAfetado ) {// @RequestBody ==> Recebe um JSON e converte para Objeto. 
		
		ClientesAfetados cliSalvo = clientesAfetadosService.save(clientesAfetado);
		
		return new ResponseEntity<ClientesAfetados>( cliSalvo, HttpStatus.OK );
	}
}
