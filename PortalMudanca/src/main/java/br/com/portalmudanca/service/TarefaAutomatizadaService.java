package br.com.portalmudanca.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.portalmudanca.model.dto.ListaMudancaNaoFechadasDTO;
import br.com.portalmudanca.repository.MudancaRepository;

@Component
@Service
public class TarefaAutomatizadaService {
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Autowired
	private MudancaRepository mdRepository;
	
	@Autowired
	ClientesAfetadosService clientesAfetadosService;
	
	//@Scheduled(initialDelay = 2000, fixedDelay = 86400000) /*Roda a cada 24 horas*/
//	@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") /*Vai rodar todo dia as 11 horas da manhã horario de Sao paulo. Doc. parametros cron ( segundo minuto hora dia_mes mes ano )  */
	@Scheduled(cron = "0 30 8 * * *", zone = "America/Sao_Paulo") /*Vai rodar todo dia as 11 horas da manhã horario de Sao paulo. Doc. parametros cron ( segundo minuto hora dia_mes mes ano )  */
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataAtual = LocalDate.now();
		List<ListaMudancaNaoFechadasDTO> listaMudancaNaoFechadas =  mdRepository.gettMudancasNaoFechadas(dataAtual);
		String textoTitulo = "";
		String textoSaldacao = "";
		
		for (ListaMudancaNaoFechadasDTO mdNaoFechada : listaMudancaNaoFechadas) {
//			int dias = Days.daysBetween(dataAtual, mdNaoFechada.getDt_final()).getDays();
			Long dias = ChronoUnit.DAYS.between( mdNaoFechada.getDt_final(), dataAtual);
			String msg = "A data prevista de fechamento para esta Mudanças foi em " + mdNaoFechada.getDt_final().format(formatter) + " : " + mdNaoFechada.getHr_final() 
					    + ", e esta com " + dias + " dias de atraso!\n\n"
					    + "Favor verificar e fechar a GMUD!";
			
			textoTitulo   = "Mudança ( ID " + mdNaoFechada.getId_mudanca()  + " ) - Pendência de Fechamento";
			textoSaldacao = "Mudanças com data de fechamento fora do prazo!";
						
			serviceSendEmail.enviarEmailHtmlTemplete( textoTitulo, 
                    serviceSendEmail.getMensagemTemplete( textoSaldacao, serviceSendEmail.getCorpoEmalGMUDNaoFechadas( msg ) ), 
                    mdNaoFechada.getEmail_solicitante()
                    ); 			
			Thread.sleep(3000);
		}
	}

	@Scheduled(cron = "0 30 2 * * *", zone = "America/Sao_Paulo") /*Vai rodar todo dia as 11 horas da manhã horario de Sao paulo. Doc. parametros cron ( segundo minuto hora dia_mes mes ano )  */
	public void updateNovosClientesPortal() {
		clientesAfetadosService.updateNovosClientes();
	}

}
