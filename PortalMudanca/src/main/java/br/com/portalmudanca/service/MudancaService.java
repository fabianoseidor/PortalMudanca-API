package br.com.portalmudanca.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portalmudanca.enums.StatusRdm;
import br.com.portalmudanca.model.ArquivosMudanca;
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.dto.ListaMudancaDTO;
import br.com.portalmudanca.model.dto.MudancaDTO;
import br.com.portalmudanca.model.dto.MudancaPadraoDTO;
import br.com.portalmudanca.repository.ArquivosMudancaRepository;
import br.com.portalmudanca.repository.MudancaRepository;
import br.com.portalmudanca.util.FormatData;


@Service
public class MudancaService {

	@Autowired
	private MudancaRepository mudancaRepository;
	
	@Autowired
	private ArquivosMudancaRepository arquivosMudancaRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;

	
	public Mudanca saveMudancaNormal( Mudanca mudanca ) {		
		return mudancaRepository.saveAndFlush(mudanca);
	}

	public Boolean existByIdMudanca( Long idMudanca ) {
		
		return mudancaRepository.findByIdMudanca(idMudanca).getId_mudanca() > 0;
	}

	public Mudanca upadateStatusMudanca( Mudanca mudanca, StatusRdm status ) {	
		
		mudanca.setStatusMudanca(status);

		return mudancaRepository.save(mudanca);
	}
	
	public Mudanca findtByIdMudanca( Long idMudanca ) {
		
		return mudancaRepository.findByIdMudanca(idMudanca);
	}

	public MudancaDTO getMudancaDTO( Mudanca mudanca ) {
		MudancaDTO mudancaDTO = new MudancaDTO();
		FormatData formatData = new FormatData();
		
		mudancaDTO.setId_mudanca          ( mudanca.getId_mudanca());
		mudancaDTO.setTitulo_mudanca      ( mudanca.getTitulo_mudanca());
		mudancaDTO.setDt_criacao          ( formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() )     );
		mudancaDTO.setDt_alteracao        ( formatData.FormataDataStringTelaDataTime2( mudanca.getDt_alteracao().toString() )    );
		mudancaDTO.setLogin_user          ( mudanca.getLogin_user());
		mudancaDTO.setStatusMudanca       ( mudanca.getStatusMudanca().getStatusRdm());
		mudancaDTO.setDescricao           ( mudanca.getDadosMudanca().getDsc_mudanca());
		mudancaDTO.setDt_Inicio_Mudanca   ( formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ));
		mudancaDTO.setHr_Inicio_Mudanca   ( mudanca.getDadosMudanca().getHr_inicio().toString());
		mudancaDTO.setDt_Conclusao_Mudanca( formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() ));
		mudancaDTO.setHr_Conclusao_Mudanca( mudanca.getDadosMudanca().getHr_final().toString() );
		mudancaDTO.setSolicitant_Mudanca  ( mudanca.getLogin_user()  ); // Aqui Tem Alteracao
		mudancaDTO.setImpacto             ( mudanca.getImpactoMudanca().getImpacto_mudanca() );
		mudancaDTO.setUrgencia            ( mudanca.getCriticidade().getCriticidade()    );
		mudancaDTO.setTipo_Mudanca        ( mudanca.getTipoMudanca().getTipo_mudanca());
		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) 
			mudancaDTO.getListaAprovadores().add(mudanca.getListaAprovadores().get(i));
		
		for(int i = 0; i < mudanca.getMudancaClientesAfetados().size(); i++)
			mudancaDTO.getMudancaClientesAfetados().add(mudanca.getMudancaClientesAfetados().get(i));
			
		return mudancaDTO;
	}
	
	public MudancaPadraoDTO getMudancaPadrao( Long id ) {
		
		MudancaPadraoDTO mudancaPadraoDTO = new MudancaPadraoDTO();
		
		Mudanca mudanca = findtByIdMudanca(id);
		
		mudancaPadraoDTO.setMudanca(mudanca);
		mudancaPadraoDTO.setTipoMudanca(mudanca.getTipoMudanca() );
		mudancaPadraoDTO.setImpactoMudanca(mudanca.getImpactoMudanca());
		mudancaPadraoDTO.setCriticidade(mudanca.getCriticidade());
		mudancaPadraoDTO.setCategoriaPadrao(mudanca.getCategoriaPadrao());
		mudancaPadraoDTO.setDadosMudanca(mudanca.getDadosMudanca());
		
		List<ArquivosMudanca> listaArquivosMudanca = new ArrayList<ArquivosMudanca>();
		listaArquivosMudanca = arquivosMudancaRepository.buscarArquivosMudancaPorIdMudanca(id);
		
		for( int i = 0; i < listaArquivosMudanca.size(); i++ )
			mudancaPadraoDTO.getArquivosMudancas().add(listaArquivosMudanca.get(i));
						
		return mudancaPadraoDTO;
	}

	
	public List<ListaMudancaDTO> getListaMudancaPorTituloDTO( String titulo ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.listaMudancaPorTitulo(titulo);
		List<ListaMudancaDTO> listaMudancaDTOs = new ArrayList<ListaMudancaDTO>();
		
		for(int i = 0; i < listaMudanca.size(); i++ ) {
			ListaMudancaDTO listaMudancaDTO = new ListaMudancaDTO();
			listaMudancaDTO.setId_mudanca    ( listaMudanca.get(i).getId_mudanca()                   );
			listaMudancaDTO.setTitulo_mudanca( listaMudanca.get(i).getTitulo_mudanca()               );
			listaMudancaDTO.setDt_criacao    ( listaMudanca.get(i).getDt_criacao()                   );
			listaMudancaDTO.setDt_alteracao  ( listaMudanca.get(i).getDt_alteracao()                 );
			listaMudancaDTO.setLogin_user    ( listaMudanca.get(i).getLogin_user()                   );
			listaMudancaDTO.setStatusMudanca ( listaMudanca.get(i).getStatusMudanca().getStatusRdm() );
			listaMudancaDTO.setTipoMudanca   ( listaMudanca.get(i).getTipoMudanca()                  );
			
			listaMudancaDTOs.add(listaMudancaDTO);
		}
		
		
		return listaMudancaDTOs;
	}

	
	public List<Mudanca> getListaMudancaPorTitulo( String titulo ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.listaMudancaPorTitulo(titulo);		
		
		return listaMudanca;
	}

	public List<Mudanca> getListaMudancaPorStatus( StatusRdm status ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.buscarMudancaPorStatus(status);		
		
		return listaMudanca;
	}

	
	public List<ListaMudancaDTO> getListaMudancaAbertas(  ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.listaMudancaAbertas();
		List<ListaMudancaDTO> listaMudancaDTOs = new ArrayList<ListaMudancaDTO>();
		
		for(int i = 0; i < listaMudanca.size(); i++ ) {
			ListaMudancaDTO listaMudancaDTO = new ListaMudancaDTO();
			listaMudancaDTO.setId_mudanca    ( listaMudanca.get(i).getId_mudanca()                   );
			listaMudancaDTO.setTitulo_mudanca( listaMudanca.get(i).getTitulo_mudanca()               );
			listaMudancaDTO.setDt_criacao    ( listaMudanca.get(i).getDt_criacao()                   );
			listaMudancaDTO.setDt_alteracao  ( listaMudanca.get(i).getDt_alteracao()                 );
			listaMudancaDTO.setLogin_user    ( listaMudanca.get(i).getLogin_user()                   );
			listaMudancaDTO.setStatusMudanca ( listaMudanca.get(i).getStatusMudanca().getStatusRdm() );
			listaMudancaDTO.setTipoMudanca   ( listaMudanca.get(i).getTipoMudanca()                  );
			
			listaMudancaDTOs.add(listaMudancaDTO);
		}
		
		
		return listaMudancaDTOs;
	}
	
	
	public void updateStatusGMUD( Long idMudaca, StatusRdm status ) {
		
		mudancaRepository.updateStatusGmud( idMudaca, status );		

	}

	public void updateFechamentoGMUD( Long idMudaca, String status ) {
		
		mudancaRepository.updateFechamentoGmud( idMudaca, status );		

	}
	
	public void sendEmailEncerramento( Long idMudanca ) throws UnsupportedEncodingException, MessagingException {
        Mudanca mudanca = mudancaRepository.getMudancaPorId(idMudanca);
        FormatData formatData = new FormatData();
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) {
			String textoTituloAprovacao   = "Fechamento de GMUD";
			String textoSaldacaoAprovacao = "A GMUD  " + mudanca.getId_mudanca() + "\n\nfoi fechada com Status " + mudanca.getStatusMudanca().getStatusRdm() + "!";
			String [][]textoInformCancel = new String[][]{ {"Número Mudança"          , mudanca.getId_mudanca().toString()                                                          },
												            {"Título da Mudança"      , mudanca.getTitulo_mudanca()                                                                 },
												            {"Descrição GMUD"         , mudanca.getDadosMudanca().getDsc_mudanca()                                                  },
												            {"Data da Solicitação"    , formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() )             },
												            {"Data Início Mudança"    , formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ) },
												            {"Hora Início Mudança"    , mudanca.getDadosMudanca().getHr_inicio().toString()                                         },
												            {"Data Conclusão Mudança" , formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )  },
												            {"Hora Conclusão Mudança" , mudanca.getDadosMudanca().getHr_final().toString()                                          },
					        					            {"Data Real do Fechamento", formatData.FormataDataStringTelaDataTime2( mudanca.getDt_fechamento().toString() )          }, 
												            {"Solicitante"            , mudanca.getLogin_user()                                                                     }, 
												            {"Status Mudança"         , mudanca.getStatusMudanca().getStatusRdm()                                                   },
												            {"Report Final"           , mudanca.getReportFinal().getReport_final()                                                  }
                };
	        serviceSendEmail.enviarEmailHtmlTemplete("Notificação: Solicitação para Aprovação da GMUD - Normal (ID " + mudanca.getId_mudanca()  + ")", 
                    serviceSendEmail.getMensagemAbertura(  textoTituloAprovacao, textoSaldacaoAprovacao, serviceSendEmail.getTrTd( textoInformCancel ) ), 
                    mudanca.getListaAprovadores().get(i).getAprovadores().getEmail()); 
		
		}
		
	}


}
