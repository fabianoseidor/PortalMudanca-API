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
import br.com.portalmudanca.model.ItensCategoriaPadrao;
import br.com.portalmudanca.repository.ItensCategoriaPadraoRepository;

@RestController
public class ItensCategoriaPadraoController {
	
	@Autowired
	ItensCategoriaPadraoRepository itensCategoriaPadraoRepository;
	
	
	@ResponseBody
	@PostMapping(value = "**/SalvarItemCategoriaPadrao")
	public ResponseEntity<ItensCategoriaPadrao> SalvarItemCategoriaPadrao( @RequestBody ItensCategoriaPadrao itemCategoriaPadrao ) throws ExceptionCustomizada{
		
		if(itemCategoriaPadrao == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Categoria Padrão. Valores vazios!");
		}
		
//		if(itemCategoriaPadrao.getId_itens_cat_padrao() == null && itensCategoriaPadraoRepository.findByTituloCatPadrao( itemCategoriaPadrao.getTituloCatPadrao() ) != null ) {
//			throw new ExceptionCustomizada("ERRO: Categoria Padrão já existe na Base de Dados!");
//		}
		
		itensCategoriaPadraoRepository.save(itemCategoriaPadrao);
		
		return new ResponseEntity<ItensCategoriaPadrao>( itemCategoriaPadrao, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/listaItemCategoriaPadrao/{id_categoria_padrao}")
	public ResponseEntity<List<ItensCategoriaPadrao>> listaItemCategoriaPadrao( @PathVariable("id_categoria_padrao") Long id_categoria_padrao ) throws ExceptionCustomizada{
		List<ItensCategoriaPadrao> itensCategoriaPadrao = itensCategoriaPadraoRepository.findByItemCategoriaPadrao( id_categoria_padrao );
		return new ResponseEntity<List<ItensCategoriaPadrao>>(itensCategoriaPadrao, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/deleteItemCategoriaPadrao/{id_categoria_padrao}")
	public ResponseEntity<String> deleteItemCategoriaPadrao( @PathVariable("id_categoria_padrao") Long id_categoria_padrao ) throws ExceptionCustomizada{
		itensCategoriaPadraoRepository.deleteById( id_categoria_padrao );
		return new ResponseEntity<String>("Sucesso", HttpStatus.OK);
	}
		
}
