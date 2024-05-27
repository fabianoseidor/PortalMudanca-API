package br.com.portalmudanca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.portalmudanca.model.ClientesAfetados;
import br.com.portalmudanca.service.ClientesAfetadosService;

@Controller
public class ClientesAfetadosController {

	@Autowired
	private ClientesAfetadosService clientesAfetadosService;
	
	
	@PostMapping("/salvarClientesAfetados")
	public ClientesAfetados salvarClientesAfetados( ClientesAfetados clientesAfetado ) {
		
		return clientesAfetadosService.save(clientesAfetado);
	}
}
