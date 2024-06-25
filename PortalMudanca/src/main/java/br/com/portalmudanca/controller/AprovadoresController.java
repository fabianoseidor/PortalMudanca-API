package br.com.portalmudanca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.Aprovadores;
import br.com.portalmudanca.repository.AprovadoresRepository;

@RestController
public class AprovadoresController  {
	
	@Autowired
	AprovadoresRepository aprovadoresRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarAprovadores")
	public ResponseEntity<Aprovadores> salvarAprovadores(@RequestBody Aprovadores aprovadores) throws ExceptionCustomizada{
		
		if(aprovadores == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Aprovadores. Valores vazios!");
		}
		
		if(aprovadores.getIdAprovadores() == null && aprovadoresRepository.existAprovador(aprovadores.getAprovador() ) != null) {
			throw new ExceptionCustomizada("ERRO: Este aprovador já existe na Base de Dados!");
		}
		
		aprovadores = aprovadoresRepository.save(aprovadores);
		
		return new ResponseEntity<Aprovadores>(aprovadores, HttpStatus.OK);		
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteAprovador")
	public ResponseEntity<String> deleteAprovador(@RequestBody Aprovadores aprovadores) throws ExceptionCustomizada{
		aprovadoresRepository.deleteById(aprovadores.getIdAprovadores());
		
		return new ResponseEntity<String>("Aprovador removido com sucesso!", HttpStatus.OK);		
	}

	//@Secured({ "ROLE_GERENTE", "ROLE_ADMIN" })
	@ResponseBody
	@DeleteMapping(value = "**/deleteProdutoPorId/{id}")
	public ResponseEntity<String> deleteAprovadoroPorId(@PathVariable("id") Long id) { 
		
		aprovadoresRepository.deleteById(id);
		
		return new ResponseEntity<String>("Produto Removido",HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAprovador/{id}")
	public ResponseEntity<Aprovadores> obterAprovador(@PathVariable("id") Long id) throws ExceptionCustomizada { 
		
		Aprovadores aprovador = aprovadoresRepository.findById(id).orElse(null);
		
		if (aprovador == null) {
			throw new ExceptionCustomizada("Não encontrou o Aprovador com código: " + id);
		}
		
		return new ResponseEntity<Aprovadores>(aprovador,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarAprovadorProdNome/{aprovador}")
	public ResponseEntity<List<Aprovadores>> buscarAprovadorProdNome(@PathVariable("aprovador") String aprovador) { 
		
		List<Aprovadores> acesso = aprovadoresRepository.buscarAprovadorNome(aprovador.toUpperCase());
		
		return new ResponseEntity<List<Aprovadores>>(acesso,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaAprovador")
	public ResponseEntity<List<Aprovadores>> listaAprovador() { 
		
		List<Aprovadores> acesso = aprovadoresRepository.buscarAprovador();
		
		return new ResponseEntity<List<Aprovadores>>(acesso,HttpStatus.OK);
	}
	
}
