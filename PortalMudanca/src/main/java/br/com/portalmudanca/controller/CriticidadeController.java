package br.com.portalmudanca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.Criticidade;
import br.com.portalmudanca.repository.CriticidadeRepository;

@RestController
public class CriticidadeController {

	@Autowired
	private CriticidadeRepository criticidadeRepository;
	
	@ResponseBody
	@PostMapping(value = "**/SalvarCriticidade")
	public ResponseEntity<Criticidade> SalvarCriticidade( @RequestBody Criticidade criticidade ) throws ExceptionCustomizada{
		
		if(criticidade == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Aprovadores. Valores vazios!");
		}
		
		if(criticidade.getId_criticidade() == null && criticidadeRepository.findByCriticidade(criticidade.getCriticidade()) != null ) {
			throw new ExceptionCustomizada("ERRO: Este Tipo Mudança já existe na Base de Dados!");
		}
		
		criticidade = criticidadeRepository.save(criticidade);
		
		return new ResponseEntity<Criticidade>(criticidade, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/listaCriticidade")
	public ResponseEntity<List<Criticidade>> listaCriticidade(  ) throws ExceptionCustomizada{
		 List<Criticidade> criticidade = criticidadeRepository.findByCriticidades();
		
		return new ResponseEntity<List<Criticidade>>(criticidade, HttpStatus.OK);
	}
	
	
}
