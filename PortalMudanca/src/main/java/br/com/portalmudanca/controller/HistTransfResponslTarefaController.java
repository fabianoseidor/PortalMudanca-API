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
import br.com.portalmudanca.model.HistoricoTransferenciaResponsavelTarefa;
import br.com.portalmudanca.repository.HistTransfResponslTarefaRepository;

@RestController
public class HistTransfResponslTarefaController {

	@Autowired
	private HistTransfResponslTarefaRepository historico;
	
	@ResponseBody
	@PostMapping(value = "**/SalvarhistoricoTrasferenciaTarefa")
	public ResponseEntity<HistoricoTransferenciaResponsavelTarefa> SalvarCriticidade( @RequestBody HistoricoTransferenciaResponsavelTarefa historicoTrasnf ) throws ExceptionCustomizada{
		
		if(historicoTrasnf == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar Aprovadores. Valores vazios!");
		}
		
		historicoTrasnf = historico.save(historicoTrasnf);
		
		return new ResponseEntity<HistoricoTransferenciaResponsavelTarefa>(historicoTrasnf, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarHistoricoTranIdMudanca/{idMudanca}")
	public ResponseEntity<List<HistoricoTransferenciaResponsavelTarefa>> buscarHistoricoTranIdMudanca(@PathVariable("idMudanca") Long idMudanca) { 
		
		List<HistoricoTransferenciaResponsavelTarefa> historicoIdMudanca = historico.findByHistoricoIdMudanca(idMudanca);
		
		return new ResponseEntity<List<HistoricoTransferenciaResponsavelTarefa>>( historicoIdMudanca, HttpStatus.OK );
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarHistoricoTranIdTarefa/{idTarefa}")
	public ResponseEntity<List<HistoricoTransferenciaResponsavelTarefa>> buscarHistoricoTranIdTarefa(@PathVariable("idTarefa") Long idTarefa) { 
		
		List<HistoricoTransferenciaResponsavelTarefa> historicoIdMudanca = historico.findByHistoricoIdTarefa(idTarefa);
		
		return new ResponseEntity<List<HistoricoTransferenciaResponsavelTarefa>>( historicoIdMudanca, HttpStatus.OK );
	}

	@ResponseBody
	@GetMapping(value = "**/buscarHistoricoTranIdHistorico/{idHistorico}")
	public ResponseEntity<List<HistoricoTransferenciaResponsavelTarefa>> buscarHistoricoTranIdHistorico(@PathVariable("idHistorico") Long idHistorico) { 
		
		List<HistoricoTransferenciaResponsavelTarefa> historicoIdMudanca = historico.findByHistoricoIdHistorico(idHistorico);
		
		return new ResponseEntity<List<HistoricoTransferenciaResponsavelTarefa>>( historicoIdMudanca, HttpStatus.OK );
	}

}
