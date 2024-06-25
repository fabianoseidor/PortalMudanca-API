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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.enums.StatusRdm;
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.dto.ListaMudancaDTO;
import br.com.portalmudanca.model.dto.MudancaDTO;
import br.com.portalmudanca.service.MudancaService;
import br.com.portalmudanca.service.ServiceSendEmail;

@RestController
public class MudancaController {
	
	@Autowired
	private MudancaService mudancaService;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;

/*
	@RequestMapping("cadastroMudanca")
	@ResponseBody
	public ModelAndView cadastro() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/cadastroMudanca.html");
		return modelAndView;
	}

	@RequestMapping("manutencaoMudanca")
	@ResponseBody
	public ModelAndView manutencao() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/manutencaoMudanca.html");
		return modelAndView;
	}
*/	
	
	@ResponseBody
	@PostMapping(value = "**/enviarEmailHtmlTemplete")
	public ResponseEntity<String> enviarEmailHtmlTemplete(  ) throws ExceptionCustomizada, UnsupportedEncodingException, MessagingException{
		
		serviceSendEmail.enviarEmailHtmlTemplete("Abertura da GMUD",  "Teste envio e-mail", "fabianoamaral.ti@gmail.com");
		
		return new ResponseEntity<String>("Einviado com Sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/salvarMudancaNormal")
	public ResponseEntity<MudancaDTO> salvarMudancaNormal( @RequestBody Mudanca mudanca ) throws ExceptionCustomizada, UnsupportedEncodingException, MessagingException{
		
		if(mudanca == null) throw new ExceptionCustomizada("ERRO ao tentar cadastrar a Mudanca. Valores vazios!");
				
		/* Informacao sobre a criciticada da Mudanca */
		if(mudanca.getCriticidade() == null) throw new ExceptionCustomizada("ERRO não foi informado o 'Tipo de Criticidade da Mudança'!");					

		/* Informacao sobre o Impacto da Mudanca */
		if(mudanca.getImpactoMudanca() == null) throw new ExceptionCustomizada("ERRO não foi informado 'o Impacto da Mudança'!");			

		/* Informacao sobre o Tipo de Mudanca */
		if(mudanca.getTipoMudanca() == null) throw new ExceptionCustomizada("ERRO não foi informado o 'Tipo de Mudança'!");			

		mudanca.setStatusMudanca(StatusRdm.AGUARDANDO_APROVACOES);
		
		/*****************************************************************************************************************/
		/* Associa o objedo da mudanca no  objeto MudancaClientesAfetados!                                               */
		if(mudanca.getMudancaClientesAfetados().size() == 0) 
			throw new ExceptionCustomizada("ERRO não foi informado o 'Os Clientes que serão afetadas pela Mudança'!");			
				
		for(int ca = 0; ca < mudanca.getMudancaClientesAfetados().size(); ca++) 
			mudanca.getMudancaClientesAfetados().get(ca).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Informacao dos arquivos da Mudanca                                                                            */
		for( int a =0; a < mudanca.getArquivosMudanca().size(); a++ )
			mudanca.getArquivosMudanca().get(a).setMudanca(mudanca);

		/*****************************************************************************************************************/
		/* Informacao da lista de clientes para solicitacao de aprovacao!                                                */
		if( mudanca.getListaAprovadores().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não informado o(s) Aprovadores da Mudança!");			
		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++)
			mudanca.getListaAprovadores().get(i).setMudanca(mudanca);

		/*****************************************************************************************************************/
		/* Associando o objeto de Report Final que serh utilizado ao fechar a GMUD                                       */
		mudanca.getReportFinal().setMudanca(mudanca);

		/*****************************************************************************************************************/
		/* Informacao das Atividades 'lizada(s) na Mudança                                                     */
		if(mudanca.getAtividadesMudanca().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não foi informado as Atividades da Mudança!");			
		
		for( int j =0; j < mudanca.getAtividadesMudanca().size(); j++)
			mudanca.getAtividadesMudanca().get(j).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Associa a Mudança as Informacoes/Dados  da Mudança                                                            */
		if(mudanca.getDadosMudanca() == null ) 
			throw new ExceptionCustomizada("ERRO não foi informado od Dados da Mudança!");			

		mudanca.getDadosMudanca().setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Informacao do Plano de Comunicação na Mudança. ( Relacao das pessoas que receberam e-mail informando          */
		/* da mudanca)                                                                                                   */
		if(mudanca.getPlanoComunicacoes().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não foi informado o Plano de Comunicação da Mudança!");			
		
		for( int j =0; j < mudanca.getPlanoComunicacoes().size(); j++)
			mudanca.getPlanoComunicacoes().get(j).setMudanca(mudanca);
		
		mudanca.getAcaoPosAtividade().setMudanca(mudanca);
		
		/***********************************************************************************************************************************************/
		/*                                                                                                                                             */
		/* Persiste no banco de dados as informacoes da GMUD.                                                                                          */
		/*                                                                                                                                             */
		/***********************************************************************************************************************************************/
		mudanca = mudancaService.saveMudancaNormal(mudanca);

		MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
		
		/* ******************************************************************************************************************************************* */
		/*                                             Monta Email de Abertura                                                                         */
		/* ******************************************************************************************************************************************* */
		String textoTitulo   = "Abertura de GMUD";
		String textoSaldacao = "Foi aberto uma nova GMUD " + mudancaDTO.getId_mudanca() + ", segue abaixo detalhe sobre a Mudança." ;
		String [][]textoAbertura = new String[][]{ {"Número Mudança"        , mudancaDTO.getId_mudanca().toString() },
			                                       {"Título da Mudança"     , mudancaDTO.getTitulo_mudanca()        },
			                                       {"Data da Solicitação"   , mudancaDTO.getDt_criacao()            },
			                                       {"Solicitante da Mudança", mudancaDTO.getSolicitant_Mudanca()    }, // Aqui Tem Alteracao
			                                       {"Status da Mudança"     , mudancaDTO.getStatusMudanca()         }
		                                           };
		String [][]textoAprovadores = new String[mudancaDTO.getListaAprovadores().size()][mudancaDTO.getListaAprovadores().size()];   

   		for(int i = 0; i < mudancaDTO.getListaAprovadores().size(); i++) {
   			textoAprovadores[i][0] = "Aprovador " + ( i + 1);
   			textoAprovadores[i][1] = mudancaDTO.getListaAprovadores().get(i).getAprovadores().getAprovador();
   		}
   		
   		String[][] matrizesJuntas = ArrayUtils.addAll(textoAbertura, textoAprovadores);
   		   		
		serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Abertura da GMUD - Normal (ID " + mudancaDTO.getId_mudanca()  + ")", 
				                                 serviceSendEmail.getMensagemAbertura(  textoTitulo, textoSaldacao, serviceSendEmail.getTrTd(matrizesJuntas) ), 
				                                 "fabiano.amaral@seidor.com"); // Aqui Tem Alteracao
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		String [][]textoCliAf = new String[mudanca.getMudancaClientesAfetados().size()][mudanca.getMudancaClientesAfetados().size()];                                         
		for(int i = 0; i < mudancaDTO.getMudancaClientesAfetados().size(); i++){
			textoCliAf[i][0] = "Cliente Afetado " + ( i + 1);
			textoCliAf[i][1] = mudancaDTO.getMudancaClientesAfetados().get(i).getClientesAfetados().getNome_cliente();
   		}

		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) {
			String textoTituloAprovacao   = "Aprovação de GMUD";
			String textoSaldacaoAprovacao = "Foi aberto uma nova GMUD " + mudancaDTO.getId_mudanca() + "\n\nSegue abaixo detalhes da mudança, para vossa apreciação e aprovação." ;
			String [][]textoAberturaAprovacao = new String[][]{ {"Número Mudança"        , mudancaDTO.getId_mudanca().toString() },
													            {"Título da Mudança"     , mudancaDTO.getTitulo_mudanca()        },
													            {"Descrição"             , mudancaDTO.getDescricao()             },
													            {"Data da Solicitação"   , mudancaDTO.getDt_criacao()            },
													            {"Data Início Mudança"   , mudancaDTO.getDt_Inicio_Mudanca()     },
													            {"Hora Início Mudança"   , mudancaDTO.getHr_Inicio_Mudanca()     },
													            {"Data Conclusão Mudança", mudancaDTO.getDt_Conclusao_Mudanca()  },
													            {"Hora Conclusão Mudança", mudancaDTO.getHr_Conclusao_Mudanca()  },
													            {"Aprovador"             , mudancaDTO.getListaAprovadores().get(i).getAprovadores().getAprovador() },
													            {"Solicitante da Mudança", mudancaDTO.getSolicitant_Mudanca()    }, // Aqui Tem Alteracao
													            {"Status da Mudança"     , mudancaDTO.getStatusMudanca()         },
													            {"Impacto"               , mudancaDTO.getImpacto()               },
													            {"Urgência"              , mudancaDTO.getUrgencia()              },
													            {"Tipo Mudança"          , mudancaDTO.getTipo_Mudanca()          }
	            };
	        String[][] matrizesJuntasAprovacao = ArrayUtils.addAll(textoAberturaAprovacao, textoCliAf);     
			serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Solicitação para Aprovação da GMUD - Normal (ID " + mudanca.getId_mudanca()  + ")", 
                    serviceSendEmail.getMensagemAbertura(  textoTituloAprovacao, textoSaldacaoAprovacao, serviceSendEmail.getTrTd(matrizesJuntasAprovacao) ), 
                    mudanca.getListaAprovadores().get(i).getAprovadores().getEmail()); 
		
		}
		
		return new ResponseEntity<MudancaDTO>(mudancaDTO, HttpStatus.OK);		
	}

	
	@ResponseBody
	@PostMapping(value = "**/salvarMudancaEmergencia")
	public ResponseEntity<MudancaDTO> salvarMudancaEmergencia( @RequestBody Mudanca mudanca ) throws ExceptionCustomizada, UnsupportedEncodingException, MessagingException{
		
		if(mudanca == null) throw new ExceptionCustomizada("ERRO ao tentar cadastrar a Mudanca. Valores vazios!");
				
		/* Informacao sobre a criciticada da Mudanca */
		if(mudanca.getCriticidade() == null) throw new ExceptionCustomizada("ERRO não foi informado o 'Tipo de Criticidade da Mudança'!");					

		/* Informacao sobre o Impacto da Mudanca */
		if(mudanca.getImpactoMudanca() == null) throw new ExceptionCustomizada("ERRO não foi informado 'o Impacto da Mudança'!");			

		/* Informacao sobre o Tipo de Mudanca */
		if(mudanca.getTipoMudanca() == null) throw new ExceptionCustomizada("ERRO não foi informado o 'Tipo de Mudança'!");			

		mudanca.setStatusMudanca(StatusRdm.AGUARDANDO_APROVACOES);
		
		/*****************************************************************************************************************/
		/* Associa o objedo da mudanca no  objeto MudancaClientesAfetados!                                               */
		if(mudanca.getMudancaClientesAfetados().size() == 0) 
			throw new ExceptionCustomizada("ERRO não foi informado o 'Os Clientes que serão afetadas pela Mudança'!");			
				
		for(int ca = 0; ca < mudanca.getMudancaClientesAfetados().size(); ca++) 
			mudanca.getMudancaClientesAfetados().get(ca).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Informacao da lista de clientes para solicitacao de aprovacao!                                                */
		if( mudanca.getListaAprovadores().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não informado o(s) Aprovadores da Mudança!");			
		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++)
			mudanca.getListaAprovadores().get(i).setMudanca(mudanca);
		/*****************************************************************************************************************/
		if( mudanca.getArqAprovacaoCliente().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não informado o(s) Arquivos de Aprovação do Cliente(s) para Mudança!");			

		/* Informacao dos arquivos de aprovacao dos clientes para a Mudanca                                              */
		for( int a =0; a < mudanca.getArqAprovacaoCliente().size(); a++ )
			mudanca.getArqAprovacaoCliente().get(a).setMudanca(mudanca);

		/*****************************************************************************************************************/
		/* Informacao dos arquivos de aprovacao dos clientes para a Mudanca                                              */
		for( int a =0; a < mudanca.getArquivosMudanca().size(); a++ )
			mudanca.getArquivosMudanca().get(a).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Associando o objeto de Report Final que serh utilizado ao fechar a GMUD                                       */
		mudanca.getReportFinal().setMudanca(mudanca);

		/*****************************************************************************************************************/
		/* Informacao das Atividades 'lizada(s) na Mudança                                                     */
		if(mudanca.getAtividadesMudanca().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não foi informado as Atividades da Mudança!");			
		
		for( int j =0; j < mudanca.getAtividadesMudanca().size(); j++)
			mudanca.getAtividadesMudanca().get(j).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Associa a Mudança as Informacoes/Dados  da Mudança                                                            */
		if(mudanca.getDadosMudanca() == null ) 
			throw new ExceptionCustomizada("ERRO não foi informado as Atividades da Mudança!");			

		mudanca.getDadosMudanca().setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Informacao do Plano de Comunicação na Mudança. ( Relacao das pessoas que receberam e-mail informando          */
		/* da mudanca)                                                                                                   */
		if(mudanca.getPlanoComunicacoes().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não foi informado o Plano de Comunicação da Mudança!");			
		
		for( int j =0; j < mudanca.getPlanoComunicacoes().size(); j++)
			mudanca.getPlanoComunicacoes().get(j).setMudanca(mudanca);
		
		mudanca.getAcaoPosAtividade().setMudanca(mudanca);
		
		/***********************************************************************************************************************************************/
		/*                                                                                                                                             */
		/* Persiste no banco de dados as informacoes da GMUD.                                                                                          */
		/*                                                                                                                                             */
		/***********************************************************************************************************************************************/
		mudanca = mudancaService.saveMudancaNormal(mudanca);

		MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
		
		/* ******************************************************************************************************************************************* */
		/*                                             Monta Email de Abertura                                                                         */
		/* ******************************************************************************************************************************************* */
		String textoTitulo   = "Abertura de GMUD";
		String textoSaldacao = "Foi aberto uma nova GMUD " + mudancaDTO.getId_mudanca() + ", segue abaixo detalhe sobre a Mudança." ;
		String [][]textoAbertura = new String[][]{ {"Número Mudança"        , mudancaDTO.getId_mudanca().toString() },
			                                       {"Título da Mudança"     , mudancaDTO.getTitulo_mudanca()        },
			                                       {"Data da Solicitação"   , mudancaDTO.getDt_criacao()            },
			                                       {"Solicitante da Mudança", mudancaDTO.getSolicitant_Mudanca()    }, // Aqui Tem Alteracao
			                                       {"Status da Mudança"     , mudancaDTO.getStatusMudanca()         }
		                                           };
		String [][]textoAprovadores = new String[mudancaDTO.getListaAprovadores().size()][mudancaDTO.getListaAprovadores().size()];   

   		for(int i = 0; i < mudancaDTO.getListaAprovadores().size(); i++) {
   			textoAprovadores[i][0] = "Aprovador " + ( i + 1);
   			textoAprovadores[i][1] = mudancaDTO.getListaAprovadores().get(i).getAprovadores().getAprovador();
   		}
   		
   		String[][] matrizesJuntas = ArrayUtils.addAll(textoAbertura, textoAprovadores);
   		   		
		serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Abertura da GMUD - Emergencia (ID " + mudancaDTO.getId_mudanca()  + ")", 
				                                 serviceSendEmail.getMensagemAbertura(  textoTitulo, textoSaldacao, serviceSendEmail.getTrTd(matrizesJuntas) ), 
				                                 "fabiano.amaral@seidor.com"); // Aqui Tem Alteracao
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		String [][]textoCliAf = new String[mudanca.getMudancaClientesAfetados().size()][mudanca.getMudancaClientesAfetados().size()];                                         
		for(int i = 0; i < mudancaDTO.getMudancaClientesAfetados().size(); i++){
			textoCliAf[i][0] = "Cliente Afetado " + ( i + 1);
			textoCliAf[i][1] = mudancaDTO.getMudancaClientesAfetados().get(i).getClientesAfetados().getNome_cliente();
   		}

		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) {
			String textoTituloAprovacao   = "Aprovação de GMUD - Emergencia";
			String textoSaldacaoAprovacao = "Foi aberto uma nova GMUD " + mudancaDTO.getId_mudanca() + "\n\nSegue abaixo detalhes da mudança, para vossa apreciação e aprovação." ;
			String [][]textoAberturaAprovacao = new String[][]{ {"Número Mudança"        , mudancaDTO.getId_mudanca().toString() },
													            {"Título da Mudança"     , mudancaDTO.getTitulo_mudanca()        },
													            {"Descrição"             , mudancaDTO.getDescricao()             },
													            {"Data da Solicitação"   , mudancaDTO.getDt_criacao()            },
													            {"Data Início Mudança"   , mudancaDTO.getDt_Inicio_Mudanca()     },
													            {"Hora Início Mudança"   , mudancaDTO.getHr_Inicio_Mudanca()     },
													            {"Data Conclusão Mudança", mudancaDTO.getDt_Conclusao_Mudanca()  },
													            {"Hora Conclusão Mudança", mudancaDTO.getHr_Conclusao_Mudanca()  },
													            {"Aprovador"             , mudancaDTO.getListaAprovadores().get(i).getAprovadores().getAprovador() },
													            {"Solicitante da Mudança", mudancaDTO.getSolicitant_Mudanca()    }, // Aqui Tem Alteracao
													            {"Status da Mudança"     , mudancaDTO.getStatusMudanca()         },
													            {"Impacto"               , mudancaDTO.getImpacto()               },
													            {"Urgência"              , mudancaDTO.getUrgencia()              },
													            {"Tipo Mudança"          , mudancaDTO.getTipo_Mudanca()          }
	            };
	        String[][] matrizesJuntasAprovacao = ArrayUtils.addAll(textoAberturaAprovacao, textoCliAf);     
			serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Solicitação para Aprovação da GMUD - Normal (ID " + mudanca.getId_mudanca()  + ")", 
                    serviceSendEmail.getMensagemAbertura(  textoTituloAprovacao, textoSaldacaoAprovacao, serviceSendEmail.getTrTd(matrizesJuntasAprovacao) ), 
                    mudanca.getListaAprovadores().get(i).getAprovadores().getEmail()); 
		
		}

		return new ResponseEntity<MudancaDTO>(mudancaDTO, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarMudancaPadrao")
	public ResponseEntity<MudancaDTO> salvarMudancaPadrao( @RequestBody Mudanca mudanca ) throws ExceptionCustomizada, UnsupportedEncodingException, MessagingException{
		
		if(mudanca == null) throw new ExceptionCustomizada("ERRO ao tentar cadastrar a Mudanca. Valores vazios!");
				
		/* Informacao sobre a criciticada da Mudanca */
		if(mudanca.getCriticidade() == null) throw new ExceptionCustomizada("ERRO não foi informado o 'Tipo de Criticidade da Mudança'!");					

		/* Informacao sobre o Impacto da Mudanca */
		if(mudanca.getImpactoMudanca() == null) throw new ExceptionCustomizada("ERRO não foi informado 'o Impacto da Mudança'!");			

		/* Informacao sobre o Tipo de Mudanca */
		if(mudanca.getTipoMudanca() == null) throw new ExceptionCustomizada("ERRO não foi informado o 'Tipo de Mudança'!");			

		mudanca.setStatusMudanca(StatusRdm.AGUARDANDO_APROVACOES);
		
		/*****************************************************************************************************************/
		/* Associa o objedo da mudanca no  objeto MudancaClientesAfetados!                                               */
		if(mudanca.getMudancaClientesAfetados().size() == 0) 
			throw new ExceptionCustomizada("ERRO não foi informado o 'Os Clientes que serão afetadas pela Mudança'!");			
				
		for(int ca = 0; ca < mudanca.getMudancaClientesAfetados().size(); ca++) 
			mudanca.getMudancaClientesAfetados().get(ca).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Informacao dos arquivos de aprovacao dos clientes para a Mudanca                                              */
		for( int a =0; a < mudanca.getArquivosMudanca().size(); a++ )
			mudanca.getArquivosMudanca().get(a).setMudanca(mudanca);

		/*****************************************************************************************************************/
		/* Informacao das Atividades 'lizada(s) na Mudança                                                     */
		if(mudanca.getAtividadesMudanca().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não foi informado as Atividades da Mudança!");			
		
		for( int j =0; j < mudanca.getAtividadesMudanca().size(); j++)
			mudanca.getAtividadesMudanca().get(j).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Associa a Mudança as Informacoes/Dados  da Mudança                                                            */
		if(mudanca.getDadosMudanca() == null ) 
			throw new ExceptionCustomizada("ERRO não foi informado as Atividades da Mudança!");			

		mudanca.getDadosMudanca().setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Associa a Mudança as Informacoes/Dados  da Mudança                                                            */
		if(mudanca.getCategoriaPadrao() == null ) 
			throw new ExceptionCustomizada("ERRO não foi informado a Categoria Padrão!");			
		
		/***********************************************************************************************************************************************/
		/*                                                                                                                                             */
		/* Persiste no banco de dados as informacoes da GMUD.                                                                                          */
		/*                                                                                                                                             */
		/***********************************************************************************************************************************************/
		mudanca = mudancaService.saveMudancaNormal(mudanca);

		MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
		
		/* ******************************************************************************************************************************************* */
		/*                                             Monta Email de Abertura                                                                         */
		/* ******************************************************************************************************************************************* */
		String textoTitulo   = "Abertura de GMUD";
		String textoSaldacao = "Foi aberto uma nova GMUD " + mudancaDTO.getId_mudanca() + ", segue abaixo detalhe sobre a Mudança." ;
		String [][]textoAbertura = new String[][]{ {"Número Mudança"        , mudancaDTO.getId_mudanca().toString() },
			                                       {"Título da Mudança"     , mudancaDTO.getTitulo_mudanca()        },
			                                       {"Categoria Padrão"      , mudanca.getCategoriaPadrao().getCategoria_padrao()},
			                                       {"Data da Solicitação"   , mudancaDTO.getDt_criacao()            },
			                                       {"Solicitante da Mudança", mudancaDTO.getSolicitant_Mudanca()    }, // Aqui Tem Alteracao
			                                       {"Status da Mudança"     , mudancaDTO.getStatusMudanca()         }
		                                           };
		
     		   		
		serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Abertura da GMUD - Emergencia (ID " + mudancaDTO.getId_mudanca()  + ")", 
				                                 serviceSendEmail.getMensagemAbertura(  textoTitulo, textoSaldacao, serviceSendEmail.getTrTd(textoAbertura) ), 
				                                 "fabiano.amaral@seidor.com"); // Aqui Tem Alteracao
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		
		return new ResponseEntity<MudancaDTO>(mudancaDTO, HttpStatus.OK);
		
	}
		
	@ResponseBody
	@GetMapping(value = "**/atualizaStatusGMUD/{id}/{statusGUMD}")
	public ResponseEntity<Mudanca> atualizaStatusGMUD( @PathVariable("id") Long id, @PathVariable("statusGUMD")  StatusRdm statusGUMD ) throws ExceptionCustomizada{
		
		Mudanca mudanca = mudancaService.findtByIdMudanca(id);
		
		if(mudanca == null) {
			throw new ExceptionCustomizada("ERRO ao tentar encontrar a Mudanca. Codigo pesquisado: " + id );
		}
				
		mudanca = mudancaService.upadateStatusMudanca(mudanca, statusGUMD);
		
		return new ResponseEntity<Mudanca>(mudanca, HttpStatus.OK);
		
	}

	@ResponseBody
	@GetMapping(value = "**/pesquisaMudancaPadrao/{id}")
	public ResponseEntity<Mudanca> pesquisaMudancaPadrao( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		
		Mudanca mudanca = mudancaService.findtByIdMudanca(id);
		
		if(mudanca == null) {
			throw new ExceptionCustomizada("A GUMD pesquisada não foi encontrada para o ID: " + id );
		}
		
		Long tipoMudanca = mudanca.getTipoMudanca().getId_tipo_mudanca();
		
		if(tipoMudanca != 3 )
			throw new ExceptionCustomizada("Esta GUMD pesquisada não é uma GUMD Padrão. Id pesquisado: " + id );
				
//		MudancaPadraoDTO mudancaPadraoDTO  = mudancaService.getMudancaPadrao(id);
		
		return new ResponseEntity<Mudanca>(mudanca, HttpStatus.OK);
		
	}

	@ResponseBody
	@GetMapping(value = "**/pesquisaMudancaEmergencial/{id}")
	public ResponseEntity<Mudanca> pesquisaMudancaEmergencial( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		
		Mudanca mudanca = mudancaService.findtByIdMudanca(id);
		
		if(mudanca == null) {
			throw new ExceptionCustomizada("A GUMD pesquisada não foi encontrada para o ID: " + id );
		}
		
		Long tipoMudanca = mudanca.getTipoMudanca().getId_tipo_mudanca();
		
		if(tipoMudanca != 2 )
			throw new ExceptionCustomizada("Esta GUMD pesquisada não é uma GUMD Emergencial. Id pesquisado: " + id );
				
//		MudancaPadraoDTO mudancaPadraoDTO  = mudancaService.getMudancaPadrao(id);
		
		return new ResponseEntity<Mudanca>(mudanca, HttpStatus.OK);
		
	}

	@ResponseBody
	@GetMapping(value = "**/pesquisaMudancaNormal/{id}")
	public ResponseEntity<Mudanca> pesquisaMudancaNormal( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		
		Mudanca mudanca = mudancaService.findtByIdMudanca(id);
		
		if(mudanca == null) {
			throw new ExceptionCustomizada("A GUMD pesquisada não foi encontrada para o ID: " + id );
		}
		
		Long tipoMudanca = mudanca.getTipoMudanca().getId_tipo_mudanca();
		
		if(tipoMudanca != 1 )
			throw new ExceptionCustomizada("Esta GUMD pesquisada não é uma GUMD Normal. Id pesquisado: " + id );
				
//		MudancaPadraoDTO mudancaPadraoDTO  = mudancaService.getMudancaPadrao(id);
		
		return new ResponseEntity<Mudanca>(mudanca, HttpStatus.OK);
		
	}

	@ResponseBody
	@GetMapping(value = "**/listaMudancaPorTituloDTO/{titulo}")
	public ResponseEntity<List<ListaMudancaDTO>> listaMudancaPorTituloDTO( @PathVariable("titulo") String titulo ) throws ExceptionCustomizada{
		
		List<ListaMudancaDTO> listaMudancaDTOs = mudancaService.getListaMudancaPorTituloDTO(titulo);
		
		if(listaMudancaDTOs == null) {
			throw new ExceptionCustomizada("Não foi encontrada GMUD's com essa descrição: " + titulo );
		}
		
		
		return new ResponseEntity<List<ListaMudancaDTO>>(listaMudancaDTOs, HttpStatus.OK);		
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaMudancaPorTitulo/{titulo}")
	public ResponseEntity<List<Mudanca>> listaMudancaPorTitulo( @PathVariable("titulo") String titulo ) throws ExceptionCustomizada{
		
		List<Mudanca> listaMudanca = mudancaService.getListaMudancaPorTitulo(titulo.toLowerCase());
		
		if(listaMudanca == null) {
			throw new ExceptionCustomizada("Não foi encontrada GMUD's com essa descrição: " + titulo );
		}
		
		
		return new ResponseEntity<List<Mudanca>>(listaMudanca, HttpStatus.OK);		
	}

	@ResponseBody
	@GetMapping(value = "**/listaMudancaPorStatus/{status}")
	public ResponseEntity<List<Mudanca>> listaMudancaPorStatus( @PathVariable("status") String status ) throws ExceptionCustomizada{
		
		StatusRdm statusRdm = StatusRdm.valueOf(status);
		
		List<Mudanca> listaMudanca = mudancaService.getListaMudancaPorStatus( statusRdm );
		
		if(listaMudanca == null) {
			throw new ExceptionCustomizada("Não foi encontrada GMUD's com este STATUS: " + status );
		}
		return new ResponseEntity<List<Mudanca>>(listaMudanca, HttpStatus.OK);		
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaMudancaAbertas")
	public ResponseEntity<List<ListaMudancaDTO>> listaMudancaAbertas( ) throws ExceptionCustomizada{
		
		List<ListaMudancaDTO> listaMudancaDTOs = mudancaService.getListaMudancaAbertas();
		
		if(listaMudancaDTOs == null) {
			throw new ExceptionCustomizada("Não existe GMUD com Status em Abertas" );
		}
		return new ResponseEntity<List<ListaMudancaDTO>>(listaMudancaDTOs, HttpStatus.OK);		
	}

	@ResponseBody
	@PostMapping("**/updadeStatuGMUD/{statusGMUD}/{idMudanca}")
	public ResponseEntity<String> updadeStatuGMUD(@PathVariable("statusGMUD") String statusGMUD, 
			                                     @PathVariable("idMudanca") Long idMudanca){
		StatusRdm statusRdm = StatusRdm.valueOf(statusGMUD);
		mudancaService.updateStatusGMUD(idMudanca, statusRdm);	
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping("**/updadeStatuFechamentoGMUD/{statusGMUD}/{idMudanca}")
	public ResponseEntity<String> updadeStatuFechamentoGMUD(@PathVariable("statusGMUD") String statusGMUD, 
			                                     @PathVariable("idMudanca") Long idMudanca){
		mudancaService.updateFechamentoGMUD(idMudanca, statusGMUD);	
		
		try {
			// Envio do e-mail de encerramento da GMD.
			mudancaService.sendEmailEncerramento(idMudanca);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}


}
