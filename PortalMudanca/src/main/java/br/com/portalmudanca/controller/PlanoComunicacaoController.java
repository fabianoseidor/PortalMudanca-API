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
import br.com.portalmudanca.model.PlanoComunicacao;
import br.com.portalmudanca.model.dto.PlanoComunicacaoDAO;
import br.com.portalmudanca.repository.PlanoComunicacaoRepository;

@RestController
public class PlanoComunicacaoController {

	@Autowired
	PlanoComunicacaoRepository planoComunicacaoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarPlanoComunicacao")
	public ResponseEntity<PlanoComunicacao> salvarPlanoComunicacao(@RequestBody PlanoComunicacao planoComunicacao) throws ExceptionCustomizada{
		
		if(planoComunicacao == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Aprovadores. Valores vazios!");
		}
		
		if(planoComunicacao.getId_plano_comunicacao() == null && planoComunicacaoRepository.existPlanoComunicacao(planoComunicacao.getEmpresa() ) != null) {
			throw new ExceptionCustomizada("ERRO: Este aprovador já existe na Base de Dados!");
		}
		
		planoComunicacao = planoComunicacaoRepository.save(planoComunicacao);
		
		return new ResponseEntity<PlanoComunicacao>(planoComunicacao, HttpStatus.OK);		
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarPlanoComunicacaoPorNomeEmpresa/{empresa}")
	public ResponseEntity<List<PlanoComunicacao>> buscarPlanoComunicacaoPorNomeEmpresa(@PathVariable("empresa") String empresa) { 
		
		List<PlanoComunicacao> planoComunicacao = planoComunicacaoRepository.findByEmpresa(empresa.toUpperCase());
		
		return new ResponseEntity<List<PlanoComunicacao>>(planoComunicacao,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaPlanoComunicacao")
	public ResponseEntity<List<PlanoComunicacao>> listaPlanoComunicacao() { 
		
		List<PlanoComunicacao> acesso = planoComunicacaoRepository.listaPlanoComunicacao();
		
		return new ResponseEntity<List<PlanoComunicacao>>(acesso,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarPlanoComunicacaoPorNomeContato/{nomeContato}")
	public ResponseEntity<List<PlanoComunicacao>> buscarPlanoComunicacaoPorNomeContato(@PathVariable("nomeContato") String nomeContato) { 
		
		List<PlanoComunicacao> planoComunicacao = planoComunicacaoRepository.findByContato(nomeContato.toUpperCase());
		
		return new ResponseEntity<List<PlanoComunicacao>>(planoComunicacao,HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscarPlanoComunicacaoById/{id}")
	public ResponseEntity<PlanoComunicacaoDAO> buscarPlanoComunicacaoById(@PathVariable("id") Long id) { 
		
		PlanoComunicacaoDAO planoComunicacao = planoComunicacaoRepository.buscaIdPlanoComunicacao(id);
		
		return new ResponseEntity<PlanoComunicacaoDAO>(planoComunicacao,HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscaPorIdPlanoComunicacao/{id}")
	public ResponseEntity<PlanoComunicacao> buscaPorIdPlanoComunicacao(@PathVariable("id") Long id) { 
		
		PlanoComunicacao planoComunicacao = planoComunicacaoRepository.buscaPorIdPlanoComunicacao(id);
		
		return new ResponseEntity<PlanoComunicacao>(planoComunicacao,HttpStatus.OK);
	}
	

}
