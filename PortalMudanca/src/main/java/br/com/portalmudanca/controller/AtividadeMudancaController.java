package br.com.portalmudanca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.AtividadeMudanca;
import br.com.portalmudanca.repository.AtividadeMudancaRepository;

@RestController
public class AtividadeMudancaController {

	@Autowired
	AtividadeMudancaRepository atividadeMudancaRepository;
	
	
	@ResponseBody
	@GetMapping(value = "**/obterListaAtividadeMudanca/{id}")
	public ResponseEntity<List<AtividadeMudanca>> obterListaAtividadeMudanca(@PathVariable("id") Long id) throws ExceptionCustomizada { 
		
		List<AtividadeMudanca> atividadeMudanca = atividadeMudancaRepository.buscaListaAtitividades(id);
		
		if (atividadeMudanca == null) {
			throw new ExceptionCustomizada("Não encontrou as Tarefas da GMUD: " + id);
		}
		
		return new ResponseEntity<List<AtividadeMudanca>>(atividadeMudanca,HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/obterPorIdAtividadeMudanca/{id}")
	public ResponseEntity<AtividadeMudanca> obterPorIdAtividadeMudanca(@PathVariable("id") Long id) throws ExceptionCustomizada { 
		
		AtividadeMudanca atividadeMudanca = atividadeMudancaRepository.buscaPorIdAtitividades(id);
		
		if (atividadeMudanca == null) {
			throw new ExceptionCustomizada("Não encontrou a Tarefa com código: " + id);
		}
		
		return new ResponseEntity<AtividadeMudanca>(atividadeMudanca,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/inicializaTarefa/{idAtividadeMudanca}")
	public ResponseEntity<String> inicializaTarefa(@PathVariable("idAtividadeMudanca") Long idAtividadeMudanca ){
		atividadeMudancaRepository.inicializaTarefa(idAtividadeMudanca);		  
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/finalizaTarefa/{idAtividadeMudanca}/{reportFinal}")
	public ResponseEntity<String> finalizaTarefa(@PathVariable("idAtividadeMudanca") Long idAtividadeMudanca, 
                                                 @PathVariable("reportFinal") String reportFinal ){
		atividadeMudancaRepository.finalizaTarefa( idAtividadeMudanca, reportFinal );		  
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/qtyAtitividadesAbertas/{idMudanca}")
	public ResponseEntity<Long> qtyAtitividadesAbertas(@PathVariable("idMudanca") Long idMudanca) throws ExceptionCustomizada { 
		
		Long qtyTarefaAber = atividadeMudancaRepository.qtyAtitividadesAbertas(idMudanca);
		
		return new ResponseEntity<Long>(qtyTarefaAber,HttpStatus.OK);
	}
	

}
