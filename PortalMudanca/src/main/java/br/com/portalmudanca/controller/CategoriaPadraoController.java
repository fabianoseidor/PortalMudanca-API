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
import br.com.portalmudanca.model.CategoriaPadrao;
import br.com.portalmudanca.repository.CategoriaPadraoRepository;

@RestController
public class CategoriaPadraoController {

	@Autowired
	private CategoriaPadraoRepository categoriaPadraoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/SalvarCategoriaPadrao")
	public ResponseEntity<CategoriaPadrao> SalvarCategoriaPadrao( @RequestBody CategoriaPadrao categoriaPadrao ) throws ExceptionCustomizada{
		
		if(categoriaPadrao == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Extensão do Arq.. Valores vazios!");
		}
		
		if(categoriaPadrao.getId_categoria_padrao() == null && categoriaPadraoRepository.findByCategoriaPadrao( categoriaPadrao.getCategoria_padrao() ) != null ) {
			throw new ExceptionCustomizada("ERRO: Extensão de Arq. já existe na Base de Dados!");
		}
		
		categoriaPadrao = categoriaPadraoRepository.save(categoriaPadrao);
		
		return new ResponseEntity<CategoriaPadrao>(categoriaPadrao, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaCategoriaPadrao")
	public ResponseEntity<List<CategoriaPadrao>> listaCategoriaPadrao(  ) throws ExceptionCustomizada{
		List<CategoriaPadrao> categoriaPadraos = categoriaPadraoRepository.listaCategoriaPadroes();
		return new ResponseEntity<List<CategoriaPadrao>>(categoriaPadraos, HttpStatus.OK);
	}

	
}
