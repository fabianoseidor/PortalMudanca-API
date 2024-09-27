package br.com.portalmudanca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@ResponseBody
	@GetMapping(value = "**/listaClientesAfetados")
	public ResponseEntity<List<ClientesAfetados>> listaClientesAfetados(  ) throws ExceptionCustomizada{
		List<ClientesAfetados> clientesAfetados = clientesAfetadosService.listaClientesAfetado();
		return new ResponseEntity<List<ClientesAfetados>>(clientesAfetados, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaClientesAfetadosAlias")
	public ResponseEntity<List<ClientesAfetados>> listaClientesAfetadosAlias(  ) throws ExceptionCustomizada{
		List<ClientesAfetados> clientesAfetados = clientesAfetadosService.listaClientesAfetadoAlias();
		return new ResponseEntity<List<ClientesAfetados>>(clientesAfetados, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterClientesAfetadosPorId/{id}")
	public ResponseEntity<ClientesAfetados> obterClientesAfetadosPorId(@PathVariable("id") Long id) throws ExceptionCustomizada { 
		
		ClientesAfetados clientesAfetados = clientesAfetadosService.findByIdClientesAfetado(id);
		
		if (clientesAfetados == null) {
			throw new ExceptionCustomizada("Não encontrou o Aprovador com código: " + id);
		}
		
		return new ResponseEntity<ClientesAfetados>(clientesAfetados,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterByIdClientesAfetadosPortal/{id}")
	public ResponseEntity<ClientesAfetados> obterByIdClientesAfetadosPortal(@PathVariable("id") Long id) throws ExceptionCustomizada { 
		
		ClientesAfetados clientesAfetados = clientesAfetadosService.findByIdClientesAfetadosPortal(id);
		
		if (clientesAfetados == null)
			throw new ExceptionCustomizada( "Não encontrou o Clienta Afetado com código: " + id );
				
		return new ResponseEntity<ClientesAfetados>( clientesAfetados,HttpStatus.OK );
	}

	@ResponseBody
	@GetMapping(value = "**/listaCicloUpdate")
	public ResponseEntity<List<String>> listaCicloUpdate( ) throws ExceptionCustomizada { 
		
		List<String> listaClienteUpdate = clientesAfetadosService.listaCicloUpdate();
				
		return new ResponseEntity<List<String>>( listaClienteUpdate, HttpStatus.OK );
	}

	@ResponseBody
	@GetMapping(value = "**/clientesAfetadosPorCiclo/{cicloUp}")
	public ResponseEntity<List<ClientesAfetados>>  listaCicloUpdate( @PathVariable("cicloUp") String cicloUp ) throws ExceptionCustomizada { 
		
		List<ClientesAfetados> listaClienteUpdate = clientesAfetadosService.clientesAfetadosPorCiclo(cicloUp);
				
		return new ResponseEntity<List<ClientesAfetados>>( listaClienteUpdate, HttpStatus.OK );
	}

	@ResponseBody
	@GetMapping(value = "**/getListClientesAfetados/{cliente}")
	public ResponseEntity<List<ClientesAfetados>>  getListClientesAfetados( @PathVariable("cliente") String cliente ) throws ExceptionCustomizada { 
		
		List<ClientesAfetados> listaClienteUpdate = clientesAfetadosService.listClientesAfetados( cliente.toUpperCase() );
				
		return new ResponseEntity<List<ClientesAfetados>>( listaClienteUpdate, HttpStatus.OK );
	}

}
