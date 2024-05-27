package br.com.portalmudanca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portalmudanca.model.ClientesAfetados;
import br.com.portalmudanca.repository.ClientesAfetadosRepository;

@Service
public class ClientesAfetadosService {

	@Autowired
	private ClientesAfetadosRepository clientesAfetadosRepository;
	
	public ClientesAfetados save( ClientesAfetados clientesAfetados ) {
		
		return clientesAfetadosRepository.save(clientesAfetados);
	}
}
