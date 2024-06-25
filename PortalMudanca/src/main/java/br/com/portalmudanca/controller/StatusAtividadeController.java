package br.com.portalmudanca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.StatusAtividade;
import br.com.portalmudanca.repository.StatusAtividadeRepository;

@RestController
public class StatusAtividadeController {

	@Autowired
	StatusAtividadeRepository statusAtividadeRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarStatusAtividade")
	public ResponseEntity<StatusAtividade> salvarStatusAtividade(@RequestBody StatusAtividade statusAtividade ) throws ExceptionCustomizada{
		
		if(statusAtividade == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Aprovadores. Valores vazios!");
		}
		
		if(statusAtividade.getId_status_atividade() == null && statusAtividadeRepository.existStatusAtividade(statusAtividade.getStatus_atividade() ) != null) {
			throw new ExceptionCustomizada("ERRO: Este aprovador já existe na Base de Dados!");
		}
		
		statusAtividade = statusAtividadeRepository.save(statusAtividade);
		
		return new ResponseEntity<StatusAtividade>(statusAtividade, HttpStatus.OK);
		
	}
	
}
