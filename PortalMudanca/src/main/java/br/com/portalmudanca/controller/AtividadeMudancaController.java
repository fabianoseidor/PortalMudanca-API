package br.com.portalmudanca.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.AtividadeMudanca;
import br.com.portalmudanca.model.HistoricoTransferenciaResponsavelTarefa;
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.dto.FinalizatTarefaDTO;
import br.com.portalmudanca.model.dto.ListaTarefaPorResponDTO;
import br.com.portalmudanca.model.dto.MudancaDTO;
import br.com.portalmudanca.repository.AtividadeMudancaRepository;
import br.com.portalmudanca.repository.HistTransfResponslTarefaRepository;
import br.com.portalmudanca.service.MudancaService;
import br.com.portalmudanca.service.ServiceSendEmail;

@RestController
public class AtividadeMudancaController {

	@Autowired
	AtividadeMudancaRepository atividadeMudancaRepository;
	
	@Autowired
	private MudancaService mudancaService;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Autowired
	private HistTransfResponslTarefaRepository histTransfResponslTarefaRepository;
	
	
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
	@GetMapping(value = "**/listaAtitividadesEmAberto/{id}")
	public ResponseEntity<List<AtividadeMudanca>> listaAtitividadesEmAberto(@PathVariable("id") Long id) throws ExceptionCustomizada { 
		
		List<AtividadeMudanca> atividadeMudanca = atividadeMudancaRepository.listaAtitividadesEmAberto(id);
		
		if (atividadeMudanca == null) {
			throw new ExceptionCustomizada("Não encontrou Tarefas Responsável de ID: " + id);
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
                                                 @PathVariable("reportFinal") String reportFinal ) throws UnsupportedEncodingException, MessagingException{
		
		String validaDtInicio = atividadeMudancaRepository.verificaInicioAtividade( idAtividadeMudanca );

		if( validaDtInicio == null || validaDtInicio.trim().isEmpty()) {
			return new ResponseEntity<String>("TAREFA_NAO_INICIADA", HttpStatus.OK);
		}
		
		atividadeMudancaRepository.finalizaTarefa( idAtividadeMudanca, reportFinal );
		
		if( atividadeMudancaRepository.checkTipoMudanca(idAtividadeMudanca) == 1 ) {
						
			Mudanca mudanca = mudancaService.findtByIdMudanca( atividadeMudancaRepository.getIdMudancaAtividade( idAtividadeMudanca ) );
			
			AtividadeMudanca atividadeMudanca = atividadeMudancaRepository.buscaPorIdAtitividades(idAtividadeMudanca);
			if( atividadeMudanca.getEnviar_email() ) {
				/* ******************************************************************************************************************************************* */
				/*                                             Monta Email de Fechamento de Tarefa                                                             */
				/* ******************************************************************************************************************************************* */
				MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
				String textoTitulo   = "Informativo de Tarefa (ID " + idAtividadeMudanca + ") fechada, Mudança - Padão (ID " + mudancaDTO.getId_mudanca()  + ")  - Seidor Cloud";
				String textoSaldacao = "Fechamento de tarefa: " ;
				serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalTaredaFechada( atividadeMudanca ) ), mudanca.getEmail_solicitante()); 
			}
		}

	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "**/finalizaTarefaBody")
	public ResponseEntity<String> finalizaTarefaBody( @RequestBody FinalizatTarefaDTO finalizatTarefaDTO ) throws UnsupportedEncodingException, MessagingException{
		
		// FinalizatTarefaDTO
		
		String validaDtInicio = atividadeMudancaRepository.verificaInicioAtividade( finalizatTarefaDTO.getIdAtividadeMudanca() );

		if( validaDtInicio == null || validaDtInicio.trim().isEmpty()) {
			return new ResponseEntity<String>("TAREFA_NAO_INICIADA", HttpStatus.OK);
		}
		
		atividadeMudancaRepository.finalizaTarefa( finalizatTarefaDTO.getIdAtividadeMudanca(), finalizatTarefaDTO.getReportFinal() );
		
		if( atividadeMudancaRepository.checkTipoMudanca( finalizatTarefaDTO.getIdAtividadeMudanca() ) == 1 ) {
						
			Mudanca mudanca = mudancaService.findtByIdMudanca( atividadeMudancaRepository.getIdMudancaAtividade( finalizatTarefaDTO.getIdAtividadeMudanca() ) );
			
			AtividadeMudanca atividadeMudanca = atividadeMudancaRepository.buscaPorIdAtitividades(finalizatTarefaDTO.getIdAtividadeMudanca());
			if( atividadeMudanca.getEnviar_email() ) {
				/* ******************************************************************************************************************************************* */
				/*                                             Monta Email de Fechamento de Tarefa                                                             */
				/* ******************************************************************************************************************************************* */
				MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
				String textoTitulo   = "Informativo de Tarefa (ID " + finalizatTarefaDTO.getIdAtividadeMudanca() + ") fechada, Mudança - Padão (ID " + mudancaDTO.getId_mudanca()  + ")  - Seidor Cloud";
				String textoSaldacao = "Fechamento de tarefa: " ;
				serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalTaredaFechada( atividadeMudanca ) ), mudanca.getEmail_solicitante()); 
			}
		}

	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "**/ignorarTarefa/{idAtividadeMudanca}/{reportFinal}/{tarefaIgnorada}")
	public ResponseEntity<String> ignorarTarefa(@PathVariable("idAtividadeMudanca") Long idAtividadeMudanca, 
                                                @PathVariable("reportFinal"       ) String reportFinal     ,
                                                @PathVariable("tarefaIgnorada"    ) String tarefaIgnorada){
		
		atividadeMudancaRepository.ignorarTarefa(idAtividadeMudanca, reportFinal, tarefaIgnorada);
		
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	
	}

	@ResponseBody
	@GetMapping(value = "**/qtyAtitividadesAbertas/{idMudanca}")
	public ResponseEntity<Long> qtyAtitividadesAbertas(@PathVariable("idMudanca") Long idMudanca) throws ExceptionCustomizada { 

		Long qtyTarefaAber = atividadeMudancaRepository.qtyAtitividadesAbertas(idMudanca);
		
		return new ResponseEntity<Long>(qtyTarefaAber,HttpStatus.OK);
	}

	
	@ResponseBody
	@GetMapping(value = "**/checkPrioridadeTarefas/{idMudanca}/{idtarefa}")
	public ResponseEntity<Boolean> checkPrioridadeTarefas(@PathVariable("idMudanca") Long idMudanca,
			                                                             @PathVariable("idtarefa" ) Long idtarefa  ) throws ExceptionCustomizada { 
		Boolean result = false;
		List<AtividadeMudanca> atividadeMudancas = atividadeMudancaRepository.buscaListaAtitividadesAbetas(idMudanca);

		if( atividadeMudancas.size() == 1) result = true; 
		else {
			
			Integer prioridadeTarefa = atividadeMudancaRepository.getPrioridade(idtarefa);
			int i = 0;
			for (AtividadeMudanca atividadeMudanca : atividadeMudancas) {				
				if( atividadeMudanca.getId_atividade_mudanca() == idtarefa && i == 0) {result = true; break;}
				else if( atividadeMudanca.getId_atividade_mudanca() == idtarefa && i > 0) break;
				i++;
				
				if(  (atividadeMudanca.getPrioridade() == null ? 0 : atividadeMudanca.getPrioridade()) >= prioridadeTarefa ) {result = true; break;}
				else  break; 								
			}
		}
		
		return new ResponseEntity<Boolean>(result,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/mudarResponsavelAtividade/{idAtividadeMudanca}/{idResponsavel}/{nomeResponsavel}/{login}")
	public ResponseEntity<String> mudarResponsavelAtividade(@PathVariable("idAtividadeMudanca") Long idAtividadeMudanca, 
			                                                @PathVariable("idResponsavel"     ) Long idResponsavel     ,
			                                                @PathVariable("nomeResponsavel"   ) String nomeResponsavel ,
			                                                @PathVariable("login"             ) String login){
		
		HistoricoTransferenciaResponsavelTarefa histTransfe = new HistoricoTransferenciaResponsavelTarefa();
			
		AtividadeMudanca atividadeMudanca = atividadeMudancaRepository.buscaPorIdAtitividades(idAtividadeMudanca);
		
		histTransfe.setId_tarefa             ( atividadeMudanca.getId_atividade_mudanca()                               );
		histTransfe.setId_mudanca            ( atividadeMudanca.getMudanca().getId_mudanca()                            );
		histTransfe.setId_responsavel_origem ( atividadeMudanca.getResponsavelAtividade().getId_responsavel_atividade() );
		histTransfe.setNome_user_origem      ( atividadeMudanca.getResponsavelAtividade().getResponsavel_atividade()    );
		histTransfe.setId_responsavel_destino( idResponsavel                                                            );
		histTransfe.setNome_user_destino     ( nomeResponsavel                                                          );
		histTransfe.setLogin_user            ( login                                                                    );
		
		atividadeMudancaRepository.mudarRespAtividade( idAtividadeMudanca, idResponsavel );	
		
		histTransfResponslTarefaRepository.save(histTransfe);
		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/getIdTipoMudanca/{idTarefa}")
	public ResponseEntity<Long> getIdTipoMudanca(@PathVariable("idTarefa") Long idTarefa) throws ExceptionCustomizada { 

		Long idTipoMudanca = atividadeMudancaRepository.getIdTipoMudanca( idTarefa );
		
		return new ResponseEntity<Long>(idTipoMudanca,HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/listaAtitividadesEmAbertoDTO/{idResponsavel}")
	public ResponseEntity<List<ListaTarefaPorResponDTO>> listaAtitividadesEmAbertoDTO(@PathVariable("idResponsavel") Long idResponsavel) throws ExceptionCustomizada { 		
		List<ListaTarefaPorResponDTO> listaTarefaPorResponDTO = atividadeMudancaRepository.listaAtitividadesEmAbertoDTO( idResponsavel );
		return new ResponseEntity<List<ListaTarefaPorResponDTO>>( listaTarefaPorResponDTO, HttpStatus.OK );
	}

	
}
