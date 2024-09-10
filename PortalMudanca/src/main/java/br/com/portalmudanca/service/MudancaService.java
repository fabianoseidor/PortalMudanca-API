package br.com.portalmudanca.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
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
import br.com.portalmudanca.repository.AtividadeMudancaRepository;
import br.com.portalmudanca.repository.MudancaRepository;
import br.com.portalmudanca.repository.ReportFinalRepository;
import br.com.portalmudanca.util.FormatData;


@Service
public class MudancaService {

	@Autowired
	private MudancaRepository mudancaRepository;
	
	@Autowired
	private ArquivosMudancaRepository arquivosMudancaRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Autowired
	private ReportFinalRepository reportFinalRepository;

	@Autowired
	private AtividadeMudancaRepository atividadeMudancaRepository;
	
	public Mudanca saveMudancaPadrao( Mudanca mudanca ) {
		return mudancaRepository.saveAndFlush(mudanca);
	}
	public Mudanca saveMudancas( Mudanca mudanca ) {	
		
		mudanca = mudancaRepository.saveAndFlush(mudanca);
		
		for(int i = 0; i < mudanca.getAtividadesMudanca().size(); i++) {
			if( mudanca.getAtividadesMudanca().get(i).getTarefa_automatica() ) {
				String di = mudanca.getDadosMudanca().getDt_inicio().toString();
				String ti = mudanca.getDadosMudanca().getHr_inicio().toString();
				String[] dsi = di.split("-");
				String[] tsi = ti.split(":");
				int ano_i = Integer.valueOf(dsi[0]);
				int mes_i = Integer.valueOf(dsi[1]);
				int dia_i = Integer.valueOf(dsi[2]);
				int hr_i = Integer.valueOf(tsi[0]);
				int mi_i = Integer.valueOf(tsi[1]);
				LocalDateTime dtInicio = LocalDateTime.of(ano_i, mes_i, dia_i, hr_i, mi_i, 0);
				
				String df = mudanca.getDadosMudanca().getDt_final().toString();
				String tf = mudanca.getDadosMudanca().getHr_final().toString();
				String[] dsf = df.split("-");
				String[] tsf = tf.split(":");
				int ano_f = Integer.valueOf(dsf[0]);
				int mes_f = Integer.valueOf(dsf[1]);
				int dia_f = Integer.valueOf(dsf[2]);				
				int hr_f  = Integer.valueOf(tsf[0]);
				int mi_f  = Integer.valueOf(tsf[1]);				
				LocalDateTime dtFim = LocalDateTime.of(ano_f, mes_f, dia_f, hr_f, mi_f, 0);

				atividadeMudancaRepository.inicializaTarefaAutomatica(dtInicio, mudanca.getAtividadesMudanca().get(i).getId_atividade_mudanca());
				atividadeMudancaRepository.finalizaTarefaAutomatica(mudanca.getAtividadesMudanca().get(i).getId_atividade_mudanca(), "Tarefa Automatica.", dtFim);				
			}
		}

		return mudanca;
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
		
		mudancaDTO.setId_mudanca          ( mudanca.getId_mudanca()                                                                    );
		mudancaDTO.setTitulo_mudanca      ( mudanca.getTitulo_mudanca()                                                                );
		mudancaDTO.setDt_criacao          ( formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() )            );
		mudancaDTO.setDt_alteracao        ( formatData.FormataDataStringTelaDataTime2( mudanca.getDt_alteracao().toString() )          );
		mudancaDTO.setLogin_user          ( mudanca.getLogin_user()                                                                    );
		mudancaDTO.setStatusMudanca       ( mudanca.getStatusMudanca().getStatusRdm()                                                  );
		mudancaDTO.setDescricao           ( mudanca.getDadosMudanca().getDsc_mudanca()                                                 );
		mudancaDTO.setDt_Inicio_Mudanca   ( formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ));
		mudancaDTO.setHr_Inicio_Mudanca   ( mudanca.getDadosMudanca().getHr_inicio().toString()                                        );
		mudancaDTO.setDt_Conclusao_Mudanca( formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() ) );
		mudancaDTO.setHr_Conclusao_Mudanca( mudanca.getDadosMudanca().getHr_final().toString()                                         );
		mudancaDTO.setSolicitant_Mudanca  ( mudanca.getLogin_user()                                                                    ); 
		mudancaDTO.setImpacto             ( mudanca.getImpactoMudanca().getImpacto_mudanca()                                           );
		mudancaDTO.setUrgencia            ( mudanca.getCriticidade().getCriticidade()                                                  );
		mudancaDTO.setTipo_Mudanca        ( mudanca.getTipoMudanca().getTipo_mudanca()                                                 );
		mudancaDTO.setEmail_solicitante   ( mudanca.getEmail_solicitante()                                                             );
		
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

	
	public List<Mudanca> getlistaMudancaClienteAfetados( Long idClienteAfetado ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.listaMudancaClienteAfetados( idClienteAfetado );		
		
		return listaMudanca;
	}

	
	public List<Mudanca> getListaMudancaPorStatus( StatusRdm status ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.buscarMudancaPorStatus(status);		
		
		return listaMudanca;
	}

	public Long qtyTotalMudancaAbertas(  ) {		
		return mudancaRepository.qtyTotalMudancaAbertas();
	}
	
	public List<ListaMudancaDTO> getListaMudancaAbertas( int offsetBegin, int offsetEnd ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.listaMudancaAbertas( offsetBegin, offsetEnd );
		listaMudanca.sort( (listaMudanca1, listaMudanca2) ->  listaMudanca2.getId_mudanca().compareTo(listaMudanca1.getId_mudanca()));
		
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

	public void updateFechamentoGMUD( Long idMudaca, String status, String reportFinal ) {
		mudancaRepository.updateFechamentoGmud( idMudaca, status );	
		reportFinalRepository.updateFechamentoReportFinal(idMudaca, reportFinal );
	}
	
	public void updateFechamentoPadraoGMUD( Long idMudaca, String status ) {
		mudancaRepository.updateFechamentoGmud( idMudaca, status );
		
		String HostName = mudancaRepository.getHostName();
		Mudanca mudanca = findtByIdMudanca(idMudaca);
		
		if( mudanca.getCategoriaPadrao().getDesligar_contrato() ) {
			//Long idDescomissionamentoPortal = mudancaRepository.getIdDescomissionamentoPortal(idMudaca);
			Long idDescomissionamentoPortal = mudanca.getDadosMudanca().getId_descomissionamento();
		    if( HostName.equalsIgnoreCase("PIBASTIANDEV") )
		         mudancaRepository.upDesligueInfraDEV(idDescomissionamentoPortal, mudanca.getLogin_user() );	
		    else mudancaRepository.upDesligueInfraPRD(idDescomissionamentoPortal, mudanca.getLogin_user() );
		}
	}

	public void sendEmailEncerramento( Long idMudanca ) throws UnsupportedEncodingException, MessagingException {
        Mudanca mudanca = mudancaRepository.getMudancaPorId(idMudanca);
        FormatData formatData = new FormatData();        
        String corpoEmail     = "";
        String tituloMudanca  = "Report de encerramento da mudança ID - " + mudanca.getId_mudanca();
        String textoSaldacao  = "Report de conclusão da Mudança realizada no dia: " + formatData.FormataDataStringTelaDataTime2( mudanca.getDt_fechamento().toString() );

        if( mudanca.getStatusMudanca().equals("MUDANCA_FIM_SUCESSO") || mudanca.getStatusMudanca().equals("MUDANCA_FIM_RESSALVA") ) {
        	corpoEmail   = serviceSendEmail.getCorpoEmalEncerramentoOK( mudanca );
        }else corpoEmail   = serviceSendEmail.getCorpoEmalEncerramentoNO_OK( mudanca );
        		
		for(int i = 0; i < mudanca.getPlanoComunicacoes().size(); i++) {
			if( mudanca.getPlanoComunicacoes().get(i).getEmail() != null )
			    serviceSendEmail.enviarEmailHtmlTemplete( tituloMudanca, serviceSendEmail.getMensagemTemplete( textoSaldacao, corpoEmail ), mudanca.getPlanoComunicacoes().get(i).getEmail() );
		}
	}


}
