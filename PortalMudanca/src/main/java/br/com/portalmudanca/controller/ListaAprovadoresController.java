package br.com.portalmudanca.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

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
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.dto.ListaAprovadoresDTO;
import br.com.portalmudanca.repository.ListaAprovadoresRepository;
import br.com.portalmudanca.repository.MudancaRepository;
import br.com.portalmudanca.service.ServiceSendEmail;

@RestController
public class ListaAprovadoresController {

	@Autowired
	ListaAprovadoresRepository listaAprovadoresRepository;
	
	@Autowired
	MudancaRepository mudancaRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;

	
	@ResponseBody
	@GetMapping(value = "**/listaMudancaAprovadocao")
	public ResponseEntity<List<ListaAprovadoresDTO>> listaAprovador() throws ExceptionCustomizada { 
		
		List<ListaAprovadoresDTO> listaAprovadoresDTO = listaAprovadoresRepository.buscarListaAprovacao( );
		if(listaAprovadoresDTO == null) {
			throw new ExceptionCustomizada("Não existe GMUD para ser aprovada" );
		}		
		return new ResponseEntity<List<ListaAprovadoresDTO>>(listaAprovadoresDTO,HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/listaGMUDAprovarPorLogin/{login}")
	public ResponseEntity<List<ListaAprovadoresDTO>> listaGMUDAprovarPorLogin( @PathVariable("login") String login ) throws ExceptionCustomizada { 
		
		List<ListaAprovadoresDTO> listaAprovadoresDTO = listaAprovadoresRepository.buscarListaAprovacaoPorAprovador( login );
		if(listaAprovadoresDTO == null) {
			throw new ExceptionCustomizada("Não existe GMUD para ser aprovada" );
		}		
		return new ResponseEntity<List<ListaAprovadoresDTO>>(listaAprovadoresDTO,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/aprovacaoGMUD/{idListaAprovadores}/{idMudanca}")
	public ResponseEntity<String> aprovacaoGMUD(@PathVariable("idListaAprovadores") Long idListaAprovadores, 
			                                    @PathVariable("idMudanca") Long idMudanca) throws UnsupportedEncodingException, MessagingException{
		
		listaAprovadoresRepository.updateAprovacaoGmud(idListaAprovadores);		  

		Long qtyAprovacao = listaAprovadoresRepository.getQtyAprovacao(idMudanca);
		
		if( qtyAprovacao == 0 ) { 
			mudancaRepository.updateStatusGmudAguardadndoExec(idMudanca);
			Mudanca mudanca = mudancaRepository.getMudancaPorId(idMudanca);
			
			String textoTitulo   = "Informativo de Aprovação de Mudança - " + mudanca.getTipoMudanca().getTipo_mudanca() + " (ID " + mudanca.getId_mudanca()  + ") - Seidor Cloud";
			String textoSaldacao = "A Mudança \"" + mudanca.getTitulo_mudanca() + "\" foi aprovada e aquarda execução!"  ;
/*	   		   		
			serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalPlanoComunicacao( mudanca ) ), mudanca.getEmail_solicitante()); 
*/			
			/* ******************************************************************************************************************************************* */
			/*                                           Envio de E-mail para Aprovacao de GMUD                                                            */
			/* ******************************************************************************************************************************************* */
			/* Retirado o fluxo e-mail para quem tem tarrefa, a pedido do Eugenio */
			for(int i = 0; i < mudanca.getAtividadesMudanca().size(); i++) {
				textoTitulo   = "Informativo de Tarefa da Mudança (ID " + mudanca.getId_mudanca()  + ") - Seidor Cloud";
				textoSaldacao = "Foi aberta a GMUD  " + mudanca.getId_mudanca() + "\n\nPara esta GMUD, foi designada a tarefa abaixo com atividade sob. sua responsabilidade.";
		   		
				if( mudanca.getAtividadesMudanca().get(i).getEnviar_email() )
				    serviceSendEmail.enviarEmailHtmlTemplete( textoTitulo, 
						                                      serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalInfoTarefa( mudanca, i ) ), 
						                                      mudanca.getAtividadesMudanca().get(i).getResponsavelAtividade().getEmail_responsavel_atividade()
						                                    ); 			
			}
			/* ******************************************************************************************************************************************* */
			/*                                 Envio de E-mail para informe das Tarefas da GMUD                                                            */
			/* ******************************************************************************************************************************************* */
			for(int i = 0; i < mudanca.getPlanoComunicacoes().size(); i++) {
				textoTitulo   = "Plano de Comunicação da Mudança (ID " + mudanca.getId_mudanca()  + ") - Seidor Cloud";
				textoSaldacao = "Nova mudança registrada, abaixo os detalhes da atividade: " ;
		   		   		
				serviceSendEmail.enviarEmailHtmlTemplete( textoTitulo, 
						                                  serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalPlanoComunicacao( mudanca ) ), 
						                                  mudanca.getPlanoComunicacoes().get(i).getEmail()
						                                  ); 						
			}			
		}
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/reprovacaoGMUD/{idListaAprovadores}/{idMudanca}")
	public ResponseEntity<String> reprovacaoGMUD(@PathVariable("idListaAprovadores") Long idListaAprovadores, 
			                                     @PathVariable("idMudanca") Long idMudanca) throws UnsupportedEncodingException, MessagingException{
		
		listaAprovadoresRepository.updateReprovacaoGmud(idListaAprovadores);		  
        mudancaRepository.updateStatusGmudRejeitada(idMudanca);
/*       
 retirado o fluxo de e-mail a pedido do Eugenio.
  
        Mudanca mudanca = mudancaRepository.getMudancaPorId(idMudanca);
 		// ******************************************************************************************************************************************* //
		//                                                                                                                                             //
		// ******************************************************************************************************************************************* //
		String textoTitulo   = "Informativo de Cancelamento/Rejeição da Mudança - " + mudanca.getTipoMudanca().getTipo_mudanca() + " (ID " + mudanca.getId_mudanca()  + ") - Seidor Cloud";
		String textoSaldacao = "A Mudança \"" + mudanca.getTitulo_mudanca() + "\" foi Cancelamento/Rejeição!"  ;
   		   		
		serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalPlanoComunicacao( mudanca ) ), mudanca.getEmail_solicitante()); 
		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) {			
			serviceSendEmail.enviarEmailHtmlTemplete( textoTitulo, 
                    serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalInfoTarefa( mudanca, i ) ), 
                    mudanca.getAtividadesMudanca().get(i).getResponsavelAtividade().getEmail_responsavel_atividade()
                    ); 			
		}

		// ******************************************************************************************************************************************* //
		//                                 Envio de E-mail para informe das Tarefas da GMUD                                                            //
		// ******************************************************************************************************************************************* //
		for(int i = 0; i < mudanca.getPlanoComunicacoes().size(); i++) {
			textoTitulo   = "Plano de Cumunicação da Mudança (ID " + mudanca.getId_mudanca()  + ") - Seidor Cloud";
			textoSaldacao = "Nova mudança registrada, abaixo os detalhes da atividade: " ;
	   		   		
			serviceSendEmail.enviarEmailHtmlTemplete( textoTitulo, 
					                                  serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalPlanoComunicacao( mudanca ) ), 
					                                  mudanca.getPlanoComunicacoes().get(i).getEmail()
					                                  ); 						
		}			
*/		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}
	

}
