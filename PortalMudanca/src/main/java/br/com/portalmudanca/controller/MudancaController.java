package br.com.portalmudanca.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

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
import br.com.portalmudanca.model.dto.FechamentoMudancaDTO;
import br.com.portalmudanca.model.dto.ListaMudancaDTO;
import br.com.portalmudanca.model.dto.MudancaDTO;
import br.com.portalmudanca.repository.MudancaRepository;
import br.com.portalmudanca.service.MudancaService;
import br.com.portalmudanca.service.ServiceSendEmail;

@RestController
public class MudancaController {
	
	@Autowired
	private MudancaService mudancaService;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Autowired
	private MudancaRepository mudancaRepository;
	
	
	
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
/*		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataAtual = LocalDate.now();
		List<ListaMudancaNaoFechadasDTO> listaMudancaNaoFechadas =  mudancaRepository.gettMudancasNaoFechadas(dataAtual);

		for (ListaMudancaNaoFechadasDTO mdNaoFechada : listaMudancaNaoFechadas) {
			Long dias = ChronoUnit.DAYS.between( mdNaoFechada.getDt_final(), dataAtual);
			String msg = "Senhores, favor verificar a GMUD - " + mdNaoFechada.getId_mudanca() + " ( " + mdNaoFechada.getTitulo_mudanca() + " ).\n"
					    + "A data de fechamento desta foi em " + mdNaoFechada.getDt_final().format(formatter) + " : " + mdNaoFechada.getHr_final() 
					    + ", com " + dias + " dias de atraso!\n\n"
					    + "Favor verificar e fechar a GMUD!\n\n"
					    + "Equipe Seidor Cloud Services";
			serviceSendEmail.enviarEmailHtml("Mudanças com data de fechamento fora do prazo!", msg.toString(), "fabiano.bolacha@gmail.com");
		}
*/		
		Mudanca mudanca = mudancaRepository.getMudancaPorId(60L);
		
		String textoTitulo   = "Informativo de Aprovação de Mudança - " + mudanca.getTipoMudanca().getTipo_mudanca() + " (ID " + mudanca.getId_mudanca()  + ") - Seidor Cloud";
		String textoSaldacao = "A Mudança \"" + mudanca.getTitulo_mudanca() + "\" foi aprovada e aquarda execução!"  ;
   		   		
		serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalPlanoComunicacao( mudanca ) ), "FABIANOAMARAL.TI@GMAIL.COM" ); 
		
//		serviceSendEmail.enviarEmailHtmlTemplete("Abertura da GMUD",  "Teste envio de E-mail 001!", "fabianoamaral.ti@gmail.com");
		
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
		mudanca = mudancaService.saveMudancas(mudanca);
		
		MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);

		/* ******************************************************************************************************************************************* */
		/*                                             Monta Email de Abertura                                                                         */
		/* ******************************************************************************************************************************************* */		
		String textoTitulo   = "Informativo de abertura de nova Mudança - Normal (ID " + mudancaDTO.getId_mudanca()  + ") - Seidor Cloud";
		String textoSaldacao = "Nova mudança registrada, abaixo os detalhes da atividade: " ;
   		   		
		serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalAbertura( mudancaDTO ) ), mudancaDTO.getEmail_solicitante()); 
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) {
			textoTitulo   = "Informativo de aprovação de Mudança - Normal (ID " + mudancaDTO.getId_mudanca()  + ") - Seidor Cloud";
		    textoSaldacao = "Nova mudança registrada, e necessita ser aprovada. Abaixo os detalhes da atividade: ";
			serviceSendEmail.enviarEmailHtmlTemplete( textoTitulo, 
					                                  serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalSolicitacaoAprovacao( mudancaDTO, i ) ),
					                                  mudanca.getListaAprovadores().get(i).getAprovadores().getEmail() ); 		
		}
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		
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
		mudanca = mudancaService.saveMudancas(mudanca);

		MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
		
		/* ******************************************************************************************************************************************* */
		/*                                             Monta Email de Abertura                                                                         */
		/* ******************************************************************************************************************************************* */		
		String textoTitulo   = "Informativo de abertura de nova Mudança - Emergencia (ID " + mudancaDTO.getId_mudanca()  + ") - Seidor Cloud";
		String textoSaldacao = "Nova mudança registrada, abaixo os detalhes da atividade: " ;
   		   		
		serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalAbertura( mudancaDTO ) ), mudancaDTO.getEmail_solicitante()); 
		/* ******************************************************************************************************************************************* */
		/*                                                                                                                                             */
		/* ******************************************************************************************************************************************* */
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++) {
			textoTitulo   = "Informativo de aprovação de Mudança - Emergencia (ID " + mudancaDTO.getId_mudanca()  + ") - Seidor Cloud";
		    textoSaldacao = "Nova mudança registrada, e necessita ser aprovada. Abaixo os detalhes da atividade:";
			serviceSendEmail.enviarEmailHtmlTemplete( textoTitulo, 
					                                  serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalSolicitacaoAprovacao( mudancaDTO, i ) ),
					                                  mudanca.getListaAprovadores().get(i).getAprovadores().getEmail() ); 		
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

		mudanca.setStatusMudanca(StatusRdm.AGUARDANDO_EXECUCAO);
		
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
		mudanca = mudancaService.saveMudancaPadrao(mudanca);

		MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
		/* ******************************************************************************************************************************************* */
		/*                                             Monta Email de Abertura                                                                         */
		/* ******************************************************************************************************************************************* */		
		String textoTitulo   = "Informativo de abertura de nova Mudança - Padrão (ID " + mudancaDTO.getId_mudanca()  + ") - Seidor Cloud";
		String textoSaldacao = "Nova mudança registrada, abaixo os detalhes da atividade: " ;
   		   		
		serviceSendEmail.enviarEmailHtmlTemplete(textoTitulo, serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalAbertura( mudancaDTO ) ), mudancaDTO.getEmail_solicitante()); 
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
	@GetMapping(value = "**/listaMudancaAbertas/{offsetBegin}/{offsetEnd}")
	public ResponseEntity<List<ListaMudancaDTO>> listaMudancaAbertas( @PathVariable("offsetBegin") int offsetBegin, 
			                                                          @PathVariable("offsetEnd"  ) int offsetEnd   ) throws ExceptionCustomizada{
		
		List<ListaMudancaDTO> listaMudancaDTOs = mudancaService.getListaMudancaAbertas( offsetBegin, offsetEnd);
		
		if(listaMudancaDTOs == null) {
			throw new ExceptionCustomizada("Não existe GMUD com Status em Abertas" );
		}
		return new ResponseEntity<List<ListaMudancaDTO>>(listaMudancaDTOs, HttpStatus.OK);		
	}

	@ResponseBody
	@GetMapping(value = "**/qtyTotalMudancaAbertas")
	public ResponseEntity<Long> qtyTotalMudancaAbertas( ) throws ExceptionCustomizada { 

		Long qtyMudAbertas = mudancaService.qtyTotalMudancaAbertas();
		
		return new ResponseEntity<Long>( qtyMudAbertas, HttpStatus.OK );
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
	@PostMapping("**/updadeStatuFechamentoGMUD/{statusGMUD}/{idMudanca}/{reportFinal}")
	public ResponseEntity<String> updadeStatuFechamentoGMUD(@PathVariable("statusGMUD" ) String statusGMUD , 
			                                                @PathVariable("idMudanca"  ) Long idMudanca    ,
			                                                @PathVariable("reportFinal") String reportFinal){
		
		mudancaService.updateFechamentoGMUD( idMudanca, statusGMUD, reportFinal );	
		
		try {
			// Envio do e-mail de encerramento da GMD.
			mudancaService.sendEmailEncerramento(idMudanca);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		
		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("**/updadeStatuFechamentoMudancaDTO")
	public ResponseEntity<String> updadeStatuFechamentoGMUD( @RequestBody FechamentoMudancaDTO fechamentoMudancaDTO ){
		
		mudancaService.updateFechamentoGMUD( fechamentoMudancaDTO.getIdMudanca(), fechamentoMudancaDTO.getStatusGMUD(), fechamentoMudancaDTO.getReportFinal() );	
		
		try {
			// Envio do e-mail de encerramento da GMD.
			mudancaService.sendEmailEncerramento(fechamentoMudancaDTO.getIdMudanca());
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		
		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("**/updadeStatuFechamentoPadraoMudancaDTO")
	public ResponseEntity<String> updadeStatuFechamentoPadraoGMUD( @RequestBody FechamentoMudancaDTO fechamentoMudancaDTO ){
		
		mudancaService.updateFechamentoPadraoGMUD( fechamentoMudancaDTO.getIdMudanca(), fechamentoMudancaDTO.getStatusGMUD() );	
		
		try {
			// Envio do e-mail de encerramento da GMD.
			mudancaService.sendEmailEncerramento( fechamentoMudancaDTO.getIdMudanca() );
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}
	
	
	
	@ResponseBody
	@PostMapping("**/updadeStatuFechamentoPadraoGMUD/{statusGMUD}/{idMudanca}")
	public ResponseEntity<String> updadeStatuFechamentoPadraoGMUD(@PathVariable("statusGMUD" ) String statusGMUD , 
			                                                      @PathVariable("idMudanca"  ) Long idMudanca    ){
		
		mudancaService.updateFechamentoPadraoGMUD( idMudanca, statusGMUD );	
		
		try {
			// Envio do e-mail de encerramento da GMD.
			mudancaService.sendEmailEncerramento(idMudanca);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/getlistaMudancaClienteAfetados/{idClienteAfetado}")
	public ResponseEntity<List<Mudanca>> getlistaMudancaClienteAfetados( @PathVariable("idClienteAfetado") Long idClienteAfetado ) throws ExceptionCustomizada{
		

		
		List<Mudanca> listaMudanca = mudancaService.getlistaMudancaClienteAfetados( idClienteAfetado );
		
		if(listaMudanca == null) {
			throw new ExceptionCustomizada("Não foi encontrada GMUD's para este Cliente!" );
		}
		return new ResponseEntity<List<Mudanca>>(listaMudanca, HttpStatus.OK);		
	}

}
