package br.com.portalmudanca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.TipoMudanca;
import br.com.portalmudanca.repository.TipoMudancaRepository;

@RestController
public class TipoMudancaController {

	@Autowired
	private TipoMudancaRepository tipoMudancaRepository;
	
	@ResponseBody
	@PostMapping(value = "**/SalvarTipoMudanca")
	public ResponseEntity<TipoMudanca> SalvarTipoMudanca( @RequestBody TipoMudanca tipoMudanca ) throws ExceptionCustomizada{
		
		if(tipoMudanca == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Aprovadores. Valores vazios!");
		}
		
		if(tipoMudanca.getId_tipo_mudanca() == null && tipoMudancaRepository.findByTipo_mudanca(tipoMudanca.getTipo_mudanca()) != null ) {
			throw new ExceptionCustomizada("ERRO: Este Tipo Mudança já existe na Base de Dados!");
		}
		
		tipoMudanca = tipoMudancaRepository.save(tipoMudanca);
		
		return new ResponseEntity<TipoMudanca>(tipoMudanca, HttpStatus.OK);
	}
}
