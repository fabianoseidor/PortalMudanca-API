package br.com.portalmudanca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.ResponsavelAtividade;
import br.com.portalmudanca.repository.ResponsavelAtividadeRepository;

@RestController
public class ResponsavelAtividadeController {

	@Autowired
	private ResponsavelAtividadeRepository responsavelAtividadeRepository;
	
	@ResponseBody
	@PostMapping(value = "**/SalvarResponsavelAtividade")
	public ResponseEntity<ResponsavelAtividade> SalvarResponsavelAtividade( @RequestBody ResponsavelAtividade responsavelAtividade ) throws ExceptionCustomizada{
		
		if(responsavelAtividade == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Extensão do Arq.. Valores vazios!");
		}
		
		if(responsavelAtividade.getId_responsavel_atividade() == null && responsavelAtividadeRepository.findByResponsavelAtividade( responsavelAtividade.getResponsavel_atividade() ) != null ) {
			throw new ExceptionCustomizada("ERRO: Extensão de Arq. já existe na Base de Dados!");
		}
		
		responsavelAtividade = responsavelAtividadeRepository.save(responsavelAtividade);
		
		return new ResponseEntity<ResponsavelAtividade>(responsavelAtividade, HttpStatus.OK);
	}
	
	
}
