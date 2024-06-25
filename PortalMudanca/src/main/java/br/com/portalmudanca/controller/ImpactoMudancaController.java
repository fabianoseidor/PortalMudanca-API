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
import br.com.portalmudanca.model.ImpactoMudanca;
import br.com.portalmudanca.repository.ImpactoMudancaRepository;

@RestController
public class ImpactoMudancaController {
	
	@Autowired
	private ImpactoMudancaRepository impactoMudancaRepository;

	
	@ResponseBody
	@PostMapping(value = "**/SalvarImpactoMudanca")
	public ResponseEntity<ImpactoMudanca> SalvarImpactoMudanca( @RequestBody ImpactoMudanca impactoMudanca ) throws ExceptionCustomizada{
		
		if(impactoMudanca == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Impacto Mudanca. Valores vazios!");
		}
		
		if(impactoMudanca.getId_impacto_mudanca() == null && impactoMudancaRepository.findByImpactoMudanca(impactoMudanca.getImpacto_mudanca()) != null ) {
			throw new ExceptionCustomizada("ERRO: Este Impacto Mudanca já existe na Base de Dados!");
		}
		
		impactoMudanca = impactoMudancaRepository.save(impactoMudanca);
		
		return new ResponseEntity<ImpactoMudanca>(impactoMudanca, HttpStatus.OK);
	}

	
	@ResponseBody
	@GetMapping(value = "**/listaImpactoMudanca")
	public ResponseEntity<List<ImpactoMudanca>> listaImpactoMudanca(  ) throws ExceptionCustomizada{
		 List<ImpactoMudanca> impactoMudanca = impactoMudancaRepository.findByImpactoMudancas();
		
		return new ResponseEntity<List<ImpactoMudanca>>(impactoMudanca, HttpStatus.OK);
	}

}
