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
import br.com.portalmudanca.model.GrupoCliente;
import br.com.portalmudanca.repository.GrupoClienteRepository;

@RestController
public class GrupoClienteController {
	
	@Autowired
	private GrupoClienteRepository grupoClienteRepository;
	
	@ResponseBody // Retorno da API 
	@PostMapping("**/salvarGrupoCliente") // Mepeando a url para receber um JSON
	public ResponseEntity<GrupoCliente> salvarGrupoCliente( @RequestBody GrupoCliente grupoCliente ) throws ExceptionCustomizada {// @RequestBody ==> Recebe um JSON e converte para Objeto. 
		
		if(grupoCliente == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar o 'Grup oCliente'. Valores vazios!");
		}

		grupoCliente = grupoClienteRepository.save(grupoCliente);
		
		return new ResponseEntity<GrupoCliente>( grupoCliente, HttpStatus.OK );
	}

	@ResponseBody
	@PostMapping(value = "**/deleteGrupoCliente")
	public ResponseEntity<String> deleteGrupoCliente(@RequestBody GrupoCliente grupoCliente) throws ExceptionCustomizada{
		grupoClienteRepository.delete(grupoCliente);
		return new ResponseEntity<String>("Grupo Cliente removido com sucesso!", HttpStatus.OK);		
	}

	@ResponseBody
	@PostMapping(value = "**/deleteGrupoClientePorId/{id}")
	public ResponseEntity<String> deleteGrupoClientePorId( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		grupoClienteRepository.deleteById(id);
		return new ResponseEntity<String>("Grupo Cliente removido com sucesso!", HttpStatus.OK);		
	}

	@ResponseBody
	@GetMapping(value = "**/listaGrupoCliente")
	public ResponseEntity<List<GrupoCliente>> listaGrupoCliente(  ) throws ExceptionCustomizada{
		List<GrupoCliente> grupoCliente = grupoClienteRepository.ListaGrupoCliente();
		return new ResponseEntity<List<GrupoCliente>>(grupoCliente, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterGrupoClientePorId/{id}")
	public ResponseEntity<GrupoCliente> obterGrupoClientePorId(@PathVariable("id") Long id) throws ExceptionCustomizada { 
		// findByIdGrupoCliente
		GrupoCliente grupoCliente = grupoClienteRepository.findByIdGrupoCliente(id);
		
		if (grupoCliente == null) {
			throw new ExceptionCustomizada("Não encontrou o Aprovador com código: " + id);
		}
		
		return new ResponseEntity<GrupoCliente>(grupoCliente,HttpStatus.OK);
	}

}
