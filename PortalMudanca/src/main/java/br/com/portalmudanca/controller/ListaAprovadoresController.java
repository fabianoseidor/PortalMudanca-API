package br.com.portalmudanca.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.ArrayUtils;
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
import br.com.portalmudanca.util.FormatData;

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
	
	@RequestMapping(method = RequestMethod.POST, value = "**/aprovacaoGMUD/{idListaAprovadores}/{idMudanca}")
	public ResponseEntity<String> aprovacaoGMUD(@PathVariable("idListaAprovadores") Long idListaAprovadores, 
			                                    @PathVariable("idMudanca") Long idMudanca) throws UnsupportedEncodingException, MessagingException{
		
		listaAprovadoresRepository.updateAprovacaoGmud(idListaAprovadores);		  

		Long qtyAprovacao = listaAprovadoresRepository.getQtyAprovacao(idMudanca);
		
		if( qtyAprovacao == 0 ) { 
			mudancaRepository.updateStatusGmudAguardadndoExec(idMudanca);
			
			Mudanca mudanca = mudancaRepository.getMudancaPorId(idMudanca);
			FormatData formatData = new FormatData();
			
			/* ******************************************************************************************************************************************* */
			/*                                 Envio de E-mail para informe das Tarefas da GMUD                                                            */
			/* ******************************************************************************************************************************************* */
			for(int i = 0; i < mudanca.getAtividadesMudanca().size(); i++) {
				String textoTituloAprovacao   = "Informativo de Tarefa de GMUD - Emergencia";
				String textoSaldacaoAprovacao = "Foi aberta a GMUD  " + mudanca.getId_mudanca() + "\n\nPara esta GMUD, foi designada a tarefa abaixo com atividade sob. sua responsabilidade." ;
				String [][]textoInformTarefas = new String[][]{ {"Número Mudança"        , mudanca.getId_mudanca().toString()                                                                          },
														            {"Título da Mudança"     , mudanca.getTitulo_mudanca()                                                                             },
														            {"Descrição GMUD"        , mudanca.getDadosMudanca().getDsc_mudanca()                                                              },
														            {"Data da Solicitação"   , formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() )                         },
														            {"Data Início Mudança"   , formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() )             },
														            {"Hora Início Mudança"   , mudanca.getDadosMudanca().getHr_inicio().toString()                                                     },
														            {"Data Conclusão Mudança", formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )              },
														            {"Hora Conclusão Mudança", mudanca.getDadosMudanca().getHr_final().toString()                                                      },
														            {"Número Tarefa"         , mudanca.getAtividadesMudanca().get(i).getId_atividade_mudanca().toString()                              },
														            {"Título Tarefa"         , mudanca.getAtividadesMudanca().get(i).getTitulo_atividade_mudanca()                                     }, // Aqui Tem Alteracao
														            {"Descrição Tarefa"      , mudanca.getAtividadesMudanca().get(i).getAtividade_mudanca()                                            },
														            {"Data Tarefa"           , formatData.FormataDataStringTelaData( mudanca.getAtividadesMudanca().get(i).getDt_tarefa().toString() ) },
														            {"Duração Tarefa"        , mudanca.getAtividadesMudanca().get(i).getDuracao().toString()                                           },
														            {"Responsável Tarefa"    , mudanca.getAtividadesMudanca().get(i).getResponsavelAtividade().getResponsavel_atividade()             }
		            };
		        
				serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Solicitação para Aprovação da GMUD - Normal (ID " + mudanca.getId_mudanca()  + ")", 
	                    serviceSendEmail.getMensagemAbertura(  textoTituloAprovacao, textoSaldacaoAprovacao, serviceSendEmail.getTrTd( textoInformTarefas ) ), 
	                    mudanca.getAtividadesMudanca().get(i).getResponsavelAtividade().getEmail_responsavel_atividade()); 
			
			}
			
			
			/* ******************************************************************************************************************************************* */
			/*                                 Envio de E-mail para informe das Tarefas da GMUD                                                            */
			/* ******************************************************************************************************************************************* */
			String [][]textoCliAf = new String[mudanca.getMudancaClientesAfetados().size()][mudanca.getMudancaClientesAfetados().size()];                                         
			for(int i = 0; i < mudanca.getMudancaClientesAfetados().size(); i++){
				textoCliAf[i][0] = "Cliente Afetado " + ( i + 1);
				textoCliAf[i][1] = mudanca.getMudancaClientesAfetados().get(i).getClientesAfetados().getNome_cliente();
	   		}

			for(int i = 0; i < mudanca.getPlanoComunicacoes().size(); i++) {
				String textoTituloAprovacao   = "Plano de Cumunicação de GMUD - Emergencia";
				String textoSaldacaoAprovacao = "Foi aberta a GMUD  " + mudanca.getId_mudanca() + "\n\nSegue abaixo detalhes da mudança, para vossa apreciação e conhecimento." ;
				String [][]textoInformPlanoComu = new String[][]{ {"Número Mudança"      , mudanca.getId_mudanca().toString()                                                          },
														        {"Título da Mudança"     , mudanca.getTitulo_mudanca()                                                                 },
														        {"Descrição GMUD"        , mudanca.getDadosMudanca().getDsc_mudanca()                                                  },
														        {"Data da Solicitação"   , formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() )             },
											                    {"Data Início Mudança"   , formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ) },
													            {"Hora Início Mudança"   , mudanca.getDadosMudanca().getHr_inicio().toString()                                         },
													            {"Data Conclusão Mudança", formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )  },
													            {"Hora Conclusão Mudança", mudanca.getDadosMudanca().getHr_final().toString()                                          },
													            {"Número Tarefa"         , mudanca.getAtividadesMudanca().get(i).getId_atividade_mudanca().toString()                  },
						        					            {"Status Mudança"        , mudanca.getStatusMudanca().getStatusRdm()                                                   }, // Aqui Tem Alteracao
													            {"Impacto"               , mudanca.getImpactoMudanca().getImpacto_mudanca()                                            },
													            {"Urgência"              , mudanca.getCriticidade().getCriticidade()                                                   },
													            {"Tipo Mudança"          , mudanca.getTipoMudanca().getTipo_mudanca()                                                  },
													            {"Responsável Tarefa"    , mudanca.getAtividadesMudanca().get(i).getResponsavelAtividade().getResponsavel_atividade()  }
		            };
		        
		            
		        String[][] matrizesJuntasPlanoComu = ArrayUtils.addAll(textoInformPlanoComu, textoCliAf);    
		            
				serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Solicitação para Aprovação da GMUD - Normal (ID " + mudanca.getId_mudanca()  + ")", 
	                    serviceSendEmail.getMensagemAbertura(  textoTituloAprovacao, textoSaldacaoAprovacao, serviceSendEmail.getTrTd( matrizesJuntasPlanoComu ) ), 
	                    mudanca.getPlanoComunicacoes().get(i).getEmail()); 
			
			}
			
		}
		
		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/reprovacaoGMUD/{idListaAprovadores}/{idMudanca}")
	public ResponseEntity<String> reprovacaoGMUD(@PathVariable("idListaAprovadores") Long idListaAprovadores, 
			                                     @PathVariable("idMudanca") Long idMudanca) throws UnsupportedEncodingException, MessagingException{
		
		listaAprovadoresRepository.updateReprovacaoGmud(idListaAprovadores);		  
        mudancaRepository.updateStatusGmudRejeitada(idMudanca);
        
        Mudanca mudanca = mudancaRepository.getMudancaPorId(idMudanca);
        FormatData formatData = new FormatData();
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		String [][]textoCliAf = new String[mudanca.getMudancaClientesAfetados().size()][mudanca.getMudancaClientesAfetados().size()];                                         
		for(int i = 0; i < mudanca.getMudancaClientesAfetados().size(); i++){
			textoCliAf[i][0] = "Cliente Afetado " + ( i + 1);
			textoCliAf[i][1] = mudanca.getMudancaClientesAfetados().get(i).getClientesAfetados().getNome_cliente();
   		}

		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) {
			String textoTituloAprovacao   = "Cancelamento/Rejeição de GMUD";
			String textoSaldacaoAprovacao = "A GMUD  " + mudanca.getId_mudanca() + "\n\nfoi Cancela/Rejeitada!" ;
			String [][]textoInformCancel = new String[][]{ {"Número Mudança"         , mudanca.getId_mudanca().toString()                                                          },
												            {"Título da Mudança"     , mudanca.getTitulo_mudanca()                                                                 },
												            {"Descrição GMUD"        , mudanca.getDadosMudanca().getDsc_mudanca()                                                  },
												            {"Data da Solicitação"   , formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() )             },
												            {"Data Início Mudança"   , formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ) },
												            {"Hora Início Mudança"   , mudanca.getDadosMudanca().getHr_inicio().toString()                                         },
												            {"Data Conclusão Mudança", formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )  },
												            {"Hora Conclusão Mudança", mudanca.getDadosMudanca().getHr_final().toString()                                          },
					        					            {"Status Mudança"        , mudanca.getStatusMudanca().getStatusRdm()                                                   }, // Aqui Tem Alteracao
												            {"Impacto"               , mudanca.getImpactoMudanca().getImpacto_mudanca()                                            },
												            {"Urgência"              , mudanca.getCriticidade().getCriticidade()                                                   },
												            {"Tipo Mudança"          , mudanca.getTipoMudanca().getTipo_mudanca()                                                  }
                };
	        String[][] matrizesJuntasAprovacao = ArrayUtils.addAll(textoInformCancel, textoCliAf);     
			serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Solicitação para Aprovação da GMUD - Normal (ID " + mudanca.getId_mudanca()  + ")", 
                    serviceSendEmail.getMensagemAbertura(  textoTituloAprovacao, textoSaldacaoAprovacao, serviceSendEmail.getTrTd(matrizesJuntasAprovacao) ), 
                    mudanca.getListaAprovadores().get(i).getAprovadores().getEmail()); 
		
		}

		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}
	

}
