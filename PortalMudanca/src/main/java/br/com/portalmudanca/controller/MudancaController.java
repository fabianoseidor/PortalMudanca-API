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
import br.com.portalmudanca.model.dto.ListaMudancaDTO;
import br.com.portalmudanca.model.dto.MudancaDTO;
import br.com.portalmudanca.model.dto.MudancaPadraoDTO;
import br.com.portalmudanca.service.MudancaService;
import br.com.portalmudanca.service.ServiceSendEmail;

@RestController
public class MudancaController {
	
	@Autowired
	private MudancaService mudancaService;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;

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

		mudanca.setStatusMudanca(StatusRdm.ABERTA);
		
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
		
		String textoEmailGmud = "Abertura da GMUD \n" + mudancaDTO.getTitulo_mudanca() +"\n" + mudanca.getDadosMudanca().getDsc_mudanca();
		
		serviceSendEmail.enviarEmailHtml("Notificação: Abertura da GMUD - Normal (ID " + mudancaDTO.getId_mudanca()  + ")", 
				                          serviceSendEmail.getMensagemAbertura("Prezado Cliente,", textoEmailGmud ), 
				                          "fabiano.amaral@seidor.com");
		
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

		mudanca.setStatusMudanca(StatusRdm.ABERTA);
		
		/*****************************************************************************************************************/
		/* Associa o objedo da mudanca no  objeto MudancaClientesAfetados!                                               */
		if(mudanca.getMudancaClientesAfetados().size() == 0) 
			throw new ExceptionCustomizada("ERRO não foi informado o 'Os Clientes que serão afetadas pela Mudança'!");			
				
		for(int ca = 0; ca < mudanca.getMudancaClientesAfetados().size(); ca++) 
			mudanca.getMudancaClientesAfetados().get(ca).setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Informacao da lista de clientes para solicitacao de aprovacao!                                                */
		if( mudanca.getListaAprovadores().size() < 1 ) 
			throw new ExceptionCustomizada("ERRO não informado o(s) Cliente(s) para aprovacao da Mudança!");			
		
		for(int i = 0; i < mudanca.getListaAprovadores().size(); i++)
			mudanca.getListaAprovadores().get(i).setMudanca(mudanca);

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
		
		String textoEmailGmud = "Abertura da GMUD \n" + mudancaDTO.getTitulo_mudanca() +"\n" + mudanca.getDadosMudanca().getDsc_mudanca();
		
		serviceSendEmail.enviarEmailHtml("Notificação: Abertura da GMUD - Emergencia (ID " + mudancaDTO.getId_mudanca()  + ")", 
				                          serviceSendEmail.getMensagemAbertura("Prezado Cliente,", textoEmailGmud ), 
				                          "fabiano.amaral@seidor.com");

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

		mudanca.setStatusMudanca(StatusRdm.ABERTA);
		
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
		/* Associando o objeto de Report Final que serh utilizado ao fechar a GMUD                                       */
		mudanca.getReportFinal().setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Associa a Mudança as Informacoes/Dados  da Mudança                                                            */
		if(mudanca.getDadosMudanca() == null ) 
			throw new ExceptionCustomizada("ERRO não foi informado as Atividades da Mudança!");			

		mudanca.getDadosMudanca().setMudanca(mudanca);
		
		/*****************************************************************************************************************/
		/* Associa a Mudança as Informacoes/Dados  da Mudança                                                            */
		if(mudanca.getCategoriaPadrao() == null ) 
			throw new ExceptionCustomizada("ERRO não foi informado a Categoria Padrão!");			
		
		System.out.println("CategoriaPadrao: " + mudanca.getCategoriaPadrao());
				
		for( int a =0; a < mudanca.getExecutorCategoriaPadrao().size(); a++ ) 
			mudanca.getExecutorCategoriaPadrao().get(a).setCategoria_padrao(mudanca.getCategoriaPadrao());			
		
		System.out.println("ExecutorCategoriaPadrao: " + mudanca.getExecutorCategoriaPadrao());

		/***********************************************************************************************************************************************/
		/*                                                                                                                                             */
		/* Persiste no banco de dados as informacoes da GMUD.                                                                                          */
		/*                                                                                                                                             */
		/***********************************************************************************************************************************************/
		mudanca = mudancaService.saveMudancaNormal(mudanca);

		MudancaDTO mudancaDTO = mudancaService.getMudancaDTO(mudanca);
		
		String textoEmailGmud = "Abertura da GMUD \n" + mudancaDTO.getTitulo_mudanca() +"\n" + mudanca.getDadosMudanca().getDsc_mudanca();
		
		serviceSendEmail.enviarEmailHtml("Notificação: Abertura da GMUD - Padrão (ID " + mudancaDTO.getId_mudanca()  + ")", 
				                          serviceSendEmail.getMensagemAbertura("Prezado Cliente,", textoEmailGmud ), 
				                          "fabiano.amaral@seidor.com");

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
	public ResponseEntity<MudancaPadraoDTO> pesquisaMudancaPadrao( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		
		Mudanca mudanca = mudancaService.findtByIdMudanca(id);
		
		if(mudanca == null) {
			throw new ExceptionCustomizada("A GUMD pesquisada não foi encontrada para o ID: " + id );
		}
		
		Long tipoMudanca = mudanca.getTipoMudanca().getId_tipo_mudanca();
		
		if(tipoMudanca != 3 )
			throw new ExceptionCustomizada("Esta GUMD pesquisada não é uma GUMD Padrão. Id pesquisado: " + id );
				
		MudancaPadraoDTO mudancaPadraoDTO  = mudancaService.getMudancaPadrao(id);
		
		return new ResponseEntity<MudancaPadraoDTO>(mudancaPadraoDTO, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/listaMudancaPorTitulo/{titulo}")
	public ResponseEntity<List<ListaMudancaDTO>> listaMudancaPorTitulo( @PathVariable("titulo") String titulo ) throws ExceptionCustomizada{
		
		List<ListaMudancaDTO> listaMudancaDTOs = mudancaService.getListaMudancaPorTitulo(titulo);
		
		if(listaMudancaDTOs == null) {
			throw new ExceptionCustomizada("Não foi encontrada GMUD's com essa descrição: " + titulo );
		}
		
		
		return new ResponseEntity<List<ListaMudancaDTO>>(listaMudancaDTOs, HttpStatus.OK);
		
	}


}
