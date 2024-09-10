package br.com.portalmudanca.controller;

import java.time.LocalDateTime;
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
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Categoria Padrão. Valores vazios!");
		}
		
//		if(categoriaPadrao.getId_categoria_padrao() == null && categoriaPadraoRepository.findByCategoriaPadrao( categoriaPadrao.getCategoria_padrao() ) != null ) {
//			throw new ExceptionCustomizada("ERRO: Categoria Padrão já existe na Base de Dados!");
//		}
		
		for(int i = 0; i < categoriaPadrao.getItensCategoriaPadrao().size(); i++)
			categoriaPadrao.getItensCategoriaPadrao().get(i).setCategoriaPadrao(categoriaPadrao);

		if(categoriaPadrao.getId_categoria_padrao() != null) {
		   final LocalDateTime atual = LocalDateTime.now();
		   categoriaPadrao.setDt_criacao(atual);
		}
		
		categoriaPadrao = categoriaPadraoRepository.save(categoriaPadrao);
		
		return new ResponseEntity<CategoriaPadrao>(categoriaPadrao, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaCategoriaPadroesTodas")
	public ResponseEntity<List<CategoriaPadrao>> listaCategoriaPadroesTodas(  ) throws ExceptionCustomizada{
		List<CategoriaPadrao> categoriaPadraos = categoriaPadraoRepository.listaCategoriaPadroesTodas();
		return new ResponseEntity<List<CategoriaPadrao>>(categoriaPadraos, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/listaCategoriaPadroesNormal")
	public ResponseEntity<List<CategoriaPadrao>> listaCategoriaPadroesNormal(  ) throws ExceptionCustomizada{
		List<CategoriaPadrao> categoriaPadraos = categoriaPadraoRepository.listaCategoriaPadroesNormal();
		return new ResponseEntity<List<CategoriaPadrao>>(categoriaPadraos, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaCategoriaPadroesDesativacao")
	public ResponseEntity<List<CategoriaPadrao>> listaCategoriaPadroesDesativacao(  ) throws ExceptionCustomizada{
		List<CategoriaPadrao> categoriaPadraos = categoriaPadraoRepository.listaCategoriaPadroesDesativacao();
		return new ResponseEntity<List<CategoriaPadrao>>(categoriaPadraos, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/listaCategoriaPadroesAtivacao")
	public ResponseEntity<List<CategoriaPadrao>> listaCategoriaPadroesAtivacao(  ) throws ExceptionCustomizada{
		List<CategoriaPadrao> categoriaPadraos = categoriaPadraoRepository.listaCategoriaPadroesAtivacao();
		return new ResponseEntity<List<CategoriaPadrao>>(categoriaPadraos, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/listaByIdCategoriaPadrao/{id_categoria_padrao}")
	public ResponseEntity<CategoriaPadrao> listaByIdCategoriaPadrao( @PathVariable("id_categoria_padrao") Long id_categoria_padrao ) throws ExceptionCustomizada{
		CategoriaPadrao categoriaPadraos = categoriaPadraoRepository.listaByIdCategoriaPadroes( id_categoria_padrao );
		
		return new ResponseEntity<CategoriaPadrao>(categoriaPadraos, HttpStatus.OK);
	}
		
	@ResponseBody
	@GetMapping(value = "**/listaCategoriaPadroesPag/{offsetBegin}/{offsetEnd}")
	public ResponseEntity<List<CategoriaPadrao>> listaCategoriaPadroesPag( @PathVariable("offsetBegin") int offsetBegin,
			                                                               @PathVariable("offsetEnd") int offsetEnd) 
			                                                            		   throws ExceptionCustomizada{
		List<CategoriaPadrao> categoriaPadraos = categoriaPadraoRepository.listaCategoriaPadroesPaginacao(offsetBegin, offsetEnd);
		return new ResponseEntity<List<CategoriaPadrao>>(categoriaPadraos, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/qtyCategoriaPadraoCadastrada")
	public ResponseEntity<Long> qtyCategoriaPadraoCadastrada( ) throws ExceptionCustomizada { 

		Long qtyCategoriaPadraoCad = categoriaPadraoRepository.qtyCategoriaPadraoCadastrada();
		
		return new ResponseEntity<Long>(qtyCategoriaPadraoCad,HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/deleteCategoriaPadrao/{idCategoriaPadrao}")
	public ResponseEntity<String> deleteCategoriaPadrao(@PathVariable Long idCategoriaPadrao) throws ExceptionCustomizada{
		categoriaPadraoRepository.deleteById( idCategoriaPadrao );
		
		return new ResponseEntity<String>("Sucesso", HttpStatus.OK);		
	}

	@ResponseBody
	@PostMapping(value = "**/chcekCategoriaPadraoMucanca/{id}")
	public ResponseEntity<Long> chcekCategoriaPadraoMucanca(@PathVariable Long id) throws ExceptionCustomizada{

		Long qtyCPMudanca = categoriaPadraoRepository.chcekCategoriaPadraoMucanca(id);
		
		return new ResponseEntity<Long>(qtyCPMudanca,HttpStatus.OK);	
	}

}
