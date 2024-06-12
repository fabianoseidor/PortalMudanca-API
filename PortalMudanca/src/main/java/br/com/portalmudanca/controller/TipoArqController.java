package br.com.portalmudanca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.TipoArq;
import br.com.portalmudanca.repository.TipoArqRepository;

@RestController
public class TipoArqController {

	@Autowired
	private TipoArqRepository tipoArqRepository;
	
	@ResponseBody
	@PostMapping(value = "**/SalvarTipoArq")
	public ResponseEntity<TipoArq> SalvarTipoArq( @RequestBody TipoArq tipoArq ) throws ExceptionCustomizada{
		
		if(tipoArq == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Extensão do Arq.. Valores vazios!");
		}
		
		if(tipoArq.getId_tipo_arq() == null && tipoArqRepository.findByTipoArq(tipoArq.getTipo_arq()) != null ) {
			throw new ExceptionCustomizada("ERRO: Extensão de Arq. já existe na Base de Dados!");
		}
		
		tipoArq = tipoArqRepository.save(tipoArq);
		
		return new ResponseEntity<TipoArq>(tipoArq, HttpStatus.OK);
	}
}
