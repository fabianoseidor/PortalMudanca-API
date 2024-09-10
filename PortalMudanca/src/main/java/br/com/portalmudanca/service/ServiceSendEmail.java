package br.com.portalmudanca.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.portalmudanca.model.AtividadeMudanca;
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.dto.MudancaDTO;
import br.com.portalmudanca.util.FormatData;

@Service
public class ServiceSendEmail {

	 
	 
	final String userName     = "emailappsmtp.39c07be65b2db29e"; //requires valid gmail id
	final String password     = "geCqAyNSGDDA";                  // correct password for gmail id
	final String host         = "smtp.zeptomail.com"; 
	final String port_tls     = "587"; 
	final String port_ssl     = "465"; 
	final String email_from   = "multicloud@seidorcloud.com.br"; 
	final String titulo_email = "Portal GMUD"; 
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void enviarEmailHtmlTemplete(String assunto, String menssagem, String emailDestino) throws MessagingException, UnsupportedEncodingException {
		
		// SimpleMailMessage message = new SimpleMailMessage();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(email_from, titulo_email);
		helper.setTo(emailDestino);
		
		helper.setSubject(assunto);
		helper.setText(menssagem, true);
        
		// Coloca o Log no corpo do E-mail
		ClassPathResource resource = new ClassPathResource("/static/img/imagemFundo.jpg");
		helper.addInline("logoImage", resource);

		mailSender.send(message);
	}
	
	
	@Async
	public void enviarEmailHtml(String assunto, String menssagem, String emailDestino) throws UnsupportedEncodingException, MessagingException {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth"           , "true"  );
		properties.put("mail.smtp.starttls.enable", "true"  ); //enable STARTTLS
		properties.put("mail.smtp.host"           , host    );
		properties.put("mail.smtp.port"           , port_tls);
        properties.put("mail.user"                , userName);
        properties.put("mail.password"            , password);
        
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		
		session.setDebug(true);
		
		Address[] toUser = InternetAddress.parse(emailDestino, false);
		
		try {	
			
			MimeMessage message = new MimeMessage(session);
		
			
			message.setFrom      (new InternetAddress(email_from, titulo_email, "UTF-8"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject   (assunto  , "UTF-8");
			message.setText      (menssagem, "UTF-8");
			message.setSentDate  (new Date());
			
			Transport.send(message);
			
/*			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email_from, titulo_email, "UTF-8"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			message.setContent(menssagem, "text/html; charset=utf-8");
*/			
			Transport.send(message);			
			
		}
		catch (MessagingException mex) {
			mex.printStackTrace();
		}
		
		
	}

	public String getCorpoEmalGMUDNaoFechadas( String corpoEmail ) {
		String textoCorpo = "";
		
		textoCorpo += "<p><b>" + corpoEmail + "</b><br><br>";

		return textoCorpo;
		
	}
	
	public String getCorpoEmalPlanoComunicacao( Mudanca mudanca ) {
		String textoCorpo = "";
		FormatData formatData  = new FormatData();
		String dataMudanca     = formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() );
		String dataHoraInicial = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ) + " " + mudanca.getDadosMudanca().getHr_inicio().toString();
		String dataHorafinal   = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )  + " " + mudanca.getDadosMudanca().getHr_final().toString();
		
		textoCorpo += "<p><b>Número Mudança</b><br>"        + mudanca.getId_mudanca().toString()               + "<br><br>";
		textoCorpo += "<p><b>Título da Mudança</b><br>"     + mudanca.getTitulo_mudanca()                      + "<br><br>";
		textoCorpo += "<p><b>Descrição GMUD</b><br>"        + mudanca.getDadosMudanca().getDsc_mudanca()       + "<br><br>";
		textoCorpo += "<p><b>Data Abertura Mudança</b><br>" + dataMudanca                                      + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Início</b><br>"      + dataHoraInicial                                  + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Conclusão </b><br>"  + dataHorafinal                                    + "<br><br>";
		textoCorpo += "<p><b>Solicitante</b><br>"           + mudanca.getLogin_user()                          + "<br><br>";
		textoCorpo += "<p><b>Status da Mudança</b><br>"     + mudanca.getStatusMudanca().getStatusRdm()        + "<br><br>";
		textoCorpo += "<p><b>Impacto</b><br>"               + mudanca.getImpactoMudanca().getImpacto_mudanca() + "<br><br>";
		textoCorpo += "<p><b>Urgência</b><br>"              + mudanca.getCriticidade().getCriticidade()        + "<br><br>";
		textoCorpo += "<p><b>Tipo Mudança</b><br>"          + mudanca.getTipoMudanca().getTipo_mudanca()       + "<br><br>";
		return textoCorpo;
		
	}

	
	public String getCorpoEmalInfoTarefa( Mudanca mudanca, int index ) {
		String textoCorpo = "";
		FormatData formatData  = new FormatData();
		String dataMudanca     = formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() );
		String dataHoraInicial = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ) + " " + mudanca.getDadosMudanca().getHr_inicio().toString();
		String dataHorafinal   = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )  + " " + mudanca.getDadosMudanca().getHr_final().toString();
		
		textoCorpo += "<p><b>Número Mudança</b><br>"        + mudanca.getId_mudanca().toString()                                                                          + "<br><br>";
		textoCorpo += "<p><b>Título da Mudança</b><br>"     + mudanca.getTitulo_mudanca()                                                                                 + "<br><br>";
		textoCorpo += "<p><b>Descrição GMUD</b><br>"        + mudanca.getDadosMudanca().getDsc_mudanca()                                                                  + "<br><br>";
		textoCorpo += "<p><b>Data Abertura Mudança</b><br>" + dataMudanca                                                                                                 + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Início</b><br>"      + dataHoraInicial                                                                                             + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Conclusão </b><br>"  + dataHorafinal                                                                                               + "<br><br>";
		textoCorpo += "<p><b>Título Tarefa</b><br>"         + mudanca.getAtividadesMudanca().get(index).getId_atividade_mudanca().toString()                              + "<br><br>";
		textoCorpo += "<p><b>Status Mudança</b><br>"        + mudanca.getAtividadesMudanca().get(index).getTitulo_atividade_mudanca()                                     + "<br><br>";
		textoCorpo += "<p><b>Descrição Tarefa</b><br>"      + mudanca.getAtividadesMudanca().get(index).getAtividade_mudanca()                                            + "<br><br>";
		textoCorpo += "<p><b>Data Tarefa</b><br>"           + formatData.FormataDataStringTelaData( mudanca.getAtividadesMudanca().get(index).getDt_tarefa().toString() ) + "<br><br>";
		textoCorpo += "<p><b>Duração Tarefa</b><br>"        + mudanca.getAtividadesMudanca().get(index).getDuracao().toString()                                           + "<br><br>";
		textoCorpo += "<p><b>Responsável Tarefa</b><br>"    + mudanca.getAtividadesMudanca().get(index).getResponsavelAtividade().getResponsavel_atividade()              + "<br><br>";
		return textoCorpo;
	}

	public String getCorpoEmalEncerramentoNO_OK( Mudanca mudanca) {
		String textoCorpo = "";
		FormatData formatData  = new FormatData();
		String dataMudanca     = formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() );
		String dataMudancaFec  = formatData.FormataDataStringTelaDataTime2( mudanca.getDt_fechamento().toString() );
		String dataHoraInicial = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ) + " " + mudanca.getDadosMudanca().getHr_inicio().toString();
		String dataHorafinal   = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )  + " " + mudanca.getDadosMudanca().getHr_final().toString();
		
		String reportFinal = "";
		if( mudanca.getTipoMudanca().getId_tipo_mudanca() == 3) reportFinal = "MUDANÇA PADRÃO, COM EXECUÇÃO FINALIZADAS COM ERRO!";
		else reportFinal = mudanca.getReportFinal().getReport_final();
		
		textoCorpo += "<p><b>Número Mudança</b><br>"         + mudanca.getId_mudanca().toString()         + "<br><br>";
		textoCorpo += "<p><b>Título da Mudança</b><br>"      + mudanca.getTitulo_mudanca()                + "<br><br>";
		textoCorpo += "<p><b>Data Abertura Mudança</b><br>"  + dataMudanca                                + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Início</b><br>"       + dataHoraInicial                            + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Conclusão</b><br>"    + dataHorafinal                              + "<br><br>";
		textoCorpo += "<p><b>Data Fechamento</b><br>"        + dataMudancaFec                             + "<br><br>";
		textoCorpo += "<p><b>Solicitante</b><br>"            + mudanca.getLogin_user()                    + "<br><br>";
		textoCorpo += "<p><b>Status Mudança</b><br>"         + mudanca.getStatusMudanca().getStatusRdm()  + "<br><br>";
		textoCorpo += "<p><b>Tipo Mudança</b><br>"           + mudanca.getTipoMudanca().getTipo_mudanca() + "<br><br>";
		textoCorpo += "<p><b>Report Final</b><br>"           + reportFinal                                + "<br><br>";
		return textoCorpo;
		
	}

	public String getCorpoEmalEncerramentoOK( Mudanca mudanca) {
		String textoCorpo = "";
		FormatData formatData  = new FormatData();
		String dataMudanca     = formatData.FormataDataStringTelaDataTime2( mudanca.getDt_criacao().toString() );
		String dataHoraInicial = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_inicio().toString() ) + " " + mudanca.getDadosMudanca().getHr_inicio().toString();
		String dataHorafinal   = formatData.FormataDataStringTelaData( mudanca.getDadosMudanca().getDt_final().toString() )  + " " + mudanca.getDadosMudanca().getHr_final().toString();
		
		String reportFinal = "";
		if( mudanca.getTipoMudanca().getId_tipo_mudanca() == 3) reportFinal = "MUDANÇA PADRÃO, COM EXECUÇÃO FINALIZADAS COM SUCESSO!";
		else reportFinal = mudanca.getReportFinal().getReport_final();

		textoCorpo += "<p><b>Número Mudança</b><br>"         + mudanca.getId_mudanca().toString()         + "<br><br>";
		textoCorpo += "<p><b>Título da Mudança</b><br>"      + mudanca.getTitulo_mudanca()                + "<br><br>";
		textoCorpo += "<p><b>Data Abertura Mudança</b><br>"  + dataMudanca                                + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Início</b><br>"       + dataHoraInicial                            + "<br><br>";
		textoCorpo += "<p><b>Data/Hora Conclusão </b><br>"   + dataHorafinal                              + "<br><br>";
		textoCorpo += "<p><b>Solicitante</b><br>"            + mudanca.getLogin_user()                    + "<br><br>";
		textoCorpo += "<p><b>Status Mudança</b><br>"         + mudanca.getStatusMudanca().getStatusRdm()  + "<br><br>";
		textoCorpo += "<p><b>Tipo Mudança</b><br>"           + mudanca.getTipoMudanca().getTipo_mudanca() + "<br><br>";
		textoCorpo += "<p><b>Report Final</b><br>"           + reportFinal                                + "<br><br>";
		return textoCorpo;
	}

	public String getCorpoEmalAbertura( MudancaDTO mudancaDTO) {
		String textoCorpo = "";
		
		textoCorpo += "<p><b>Número Mudança</b><br>"         + mudancaDTO.getId_mudanca().toString() + "<br><br>";
		textoCorpo += "<p><b>Título da Mudança</b><br>"      + mudancaDTO.getTitulo_mudanca()        + "<br><br>";
		textoCorpo += "<p><b>Data da Solicitação</b><br>"    + mudancaDTO.getDt_criacao()            + "<br><br>";
		textoCorpo += "<p><b>Solicitante da Mudança</b><br>" + mudancaDTO.getSolicitant_Mudanca()    + "<br><br>";
		textoCorpo += "<p><b>Status da Mudança</b><br>"      + mudancaDTO.getStatusMudanca()         + "<br><br>";
		textoCorpo += "<p><b>Tipo Mudança</b><br>"           + mudancaDTO.getTipo_Mudanca()          + "<br><br>";
		
		return textoCorpo;
	}
	
	public String getCorpoEmalTaredaFechada( AtividadeMudanca atividadeMudanca ) {
		String textoCorpo = "";
		
		textoCorpo += "<p><b>Número Mudança</b><br>"     + atividadeMudanca.getMudanca().getId_mudanca()               + "<br><br>";
		textoCorpo += "<p><b>Título da Mudança</b><br>"  + atividadeMudanca.getMudanca().getTitulo_mudanca()           + "<br><br>";
		textoCorpo += "<p><b>Número Tarefa</b><br>"      + atividadeMudanca.getId_atividade_mudanca()                  + "<br><br>";
		textoCorpo += "<p><b>Título da Tarefa</b><br>"   + atividadeMudanca.getAtividade_mudanca()                     + "<br><br>";
		textoCorpo += "<p><b>Data Início tarefa</b><br>" + atividadeMudanca.getDt_inicio_tarefa()                      + "<br><br>";
		textoCorpo += "<p><b>Data Fim tarefa</b><br>"    + atividadeMudanca.getDt_final_tarefa()                       + "<br><br>";
		textoCorpo += "<p><b>Status Tarefa</b><br>"      + atividadeMudanca.getStatusAtividade().getStatus_atividade() + "<br><br>";
		
		return textoCorpo;
	}
	
	public String getCorpoEmalSolicitacaoAprovacao( MudancaDTO mudancaDTO, int index) {
		String textoCorpo = "";
		
		String botaoURL = "http://10.154.20.134:8080/gestaodemudanca/manutencao/cadAprovacaoGMUD.jsp";
		String textoBotao = "<a href=\"" + botaoURL + "\">Link Aprovação da Mudança</a>";
				
		textoCorpo += "<p><b>Número Mudança</b><br>"         + mudancaDTO.getId_mudanca().toString()                                       + "<br><br>";
		textoCorpo += "<p><b>Título da Mudança</b><br>"      + mudancaDTO.getTitulo_mudanca()                                              + "<br><br>";
		textoCorpo += "<p><b>Descrição</b><br>"              + mudancaDTO.getDescricao()                                                   + "<br><br>";
		textoCorpo += "<p><b>Data da Solicitação</b><br>"    + mudancaDTO.getDt_criacao()                                                  + "<br><br>";
		textoCorpo += "<p><b>Data Início Mudança</b><br>"    + mudancaDTO.getDt_Inicio_Mudanca()                                           + "<br><br>";
		textoCorpo += "<p><b>Hora Início Mudança</b><br>"    + mudancaDTO.getHr_Inicio_Mudanca()                                           + "<br><br>";
		textoCorpo += "<p><b>Data Conclusão Mudança</b><br>" + mudancaDTO.getDt_Conclusao_Mudanca()                                        + "<br><br>";
		textoCorpo += "<p><b>Hora Conclusão Mudança</b><br>" + mudancaDTO.getHr_Conclusao_Mudanca()                                        + "<br><br>";
		textoCorpo += "<p><b>Aprovador</b><br>"              + mudancaDTO.getListaAprovadores().get(index).getAprovadores().getAprovador() + "<br><br>";
		textoCorpo += "<p><b>Solicitante da Mudança</b><br>" + mudancaDTO.getTipo_Mudanca()                                                + "<br><br>";
		textoCorpo += "<p><b>Status da Mudança</b><br>"      + mudancaDTO.getSolicitant_Mudanca()                                          + "<br><br>";
		textoCorpo += "<p><b>Impacto</b><br>"                + mudancaDTO.getImpacto()                                                     + "<br><br>";
		textoCorpo += "<p><b>Urgência</b><br>"               + mudancaDTO.getUrgencia()                                                    + "<br><br>";
		textoCorpo += "<p><b>Tipo Mudança</b><br>"           + mudancaDTO.getTipo_Mudanca()                                                + "<br><br>";
		

		for(int i = 0; i < mudancaDTO.getMudancaClientesAfetados().size(); i++)
			textoCorpo += "<p><b>Cliente Afetado</b><br>" + mudancaDTO.getMudancaClientesAfetados().get(i).getClientesAfetados().getNome_cliente() + "<br><br>";
		
		textoCorpo += "<p><b>" + textoBotao + "</b><br><br>";

		return textoCorpo;
	}

	public String getMensagemTemplete( String textoSaldacao, String textoCorpo ) {
		
		String testo = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
				+ "  xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" "
				+ "  xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns=\"http://www.w3.org/TR/REC-html40\" lang=\"en\"> "
				+ "<head> "
				+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=\" UTF-8\"> "
				+ "  <meta name=\"GENERATOR\" content=\"MSHTML 11.00.10570.1001\"> <!--[if !mso]><style> "
				+ "v\\:* {behavior:url(#default#VML);} "
				+ "o\\:* {behavior:url(#default#VML);} "
				+ "w\\:* {behavior:url(#default#VML);} "
				+ ".shape {behavior:url(#default#VML);} "
				+ "</style><![endif]--> "
				+ "  <style> "
				+ "    <!--/* Font Definitions */ "
				+ "    @font-face { "
				+ "      font-family: Helvetica; "
				+ "      panose-1: 2 11 6 4 2 2 2 2 2 4; "
				+ "    } "
				 
				+ "    @font-face { "
				+ "      font-family: \"Cambria Math\"; "
				+ "      panose-1: 2 4 5 3 5 4 6 3 2 4; "
				+ "    } "
				 
				+ "    @font-face { "
				+ "      font-family: Calibri; "
				+ "      panose-1: 2 15 5 2 2 2 4 3 2 4; "
				+ "    } "
				 
				+ "    /* Style Definitions */ "
				+ "    p.MsoNormal, "
				+ "    li.MsoNormal, "
				+ "    div.MsoNormal { "
				+ "      margin: 0cm; "
				+ "      font-size: 11.0pt; "
				+ "      font-family: \"Calibri\", sans-serif; "
				+ "    } "
				 
				+ "    a:link, "
				+ "    span.MsoHyperlink { "
				+ "      mso-style-priority: 99; "
				+ "      color: blue; "
				+ "      text-decoration: underline; "
				+ "    } "
				 
				+ "    p.elementtoproof, "
				+ "    li.elementtoproof, "
				+ "    div.elementtoproof { "
				+ "      mso-style-name: elementtoproof; "
				+ "      margin: 0cm; "
				+ "      font-size: 11.0pt; "
				+ "      font-family: \"Calibri\", sans-serif; "
				+ "    } "
				 
				+ "    span.EstiloDeEmail20 { "
				+ "      mso-style-type: personal-reply; "
				+ "      font-family: \"Calibri\", sans-serif; "
				+ "      color: windowtext; "
				+ "    } "
				 
				+ "    .MsoChpDefault { "
				+ "      mso-style-type: export-only; "
				+ "      font-size: 10.0pt; "
				+ "    } "
				 
				+ "    @page WordSection1 { "
				+ "      size: 612.0pt 792.0pt; "
				+ "      margin: 70.85pt 3.0cm 70.85pt 3.0cm; "
				+ "    } "
				 
				+ "    div.WordSection1 { "
				+ "      page: WordSection1; "
				+ "    } "
				+ "    --> "
				+ "  </style> "

				+ "</head> "
				 
				+ "<body lang=\"PT-BR\" style=\"-ms-word-wrap: break-word;\" link=\"blue\" vlink=\"purple\"> "
				+ "  <div class=\"WordSection1\"> "
				+ "    <div> "
				+ "      <p class=\"MsoNormal\" style=\"background: white;\"><span style=\"color: black; font-size: 12pt;\"><img width=\"1\" "
				+ "            height=\"1\" id=\"_x0000_i1026\" style=\"width: 0.008in; height: 0.008in;\" "
				+ "            src=\"https://t.rdsv1.net/wf/open?upn=PR9bgHP6IBGbBLV9w3Gkg9o4nsI6EYm2ypj5mY-2FvpyYlhhwQIfHjgTf1MET69cpnPbWu7q7LcCNg9xY5nknRv8C-2BieqjWccot5gv-2F-2BdqZjz5Sn9n4GHXUDxc-2Fn83OZ-2BqxV9XI9rQ881hmZ2niyS-2FgjcgzKaeGUmLS5ltCKtP9Jb2-2FHsmWons40Cp-2BktX5dYeRIAQgzNt5EHRf6KV6MCIFmMag7wloztzZ6LxWrVE0BsrguTMKkqN2F0GHlD2FGrIPlyZQtSAKXmdDRINIcp4tSg3A05IODoKXxQvqFBFpqHYB96g93Cxs8ENkg4nbpUVEbvZhzTwv8aswT0cBup12TePHyvzvJwTlFmwMoTcCcd9jLbcUuTx-2B25A0ENVR6DhhG8JOuMtHNQXPBMsX2rdPuYCEqQ0BJh9BJRXmR7Dso3uExetTivauveJItPLdiHL-2BaVKCxhTalntGK-2FBylO-2BAZ4-2FmZAgCcr7K8n7IkEv13jj-2FsNQaIRr-2FneedegxPHcxWb2ioJ-2F8kghijBkQA51U4tfwFQQ-2BCIc8MogQnvlqJzRTTb42qDcZChI6laRMaFAKMTRpRqcswEUIZTpgEg47kb2gt6-2BFKaEaI6ipdqptPOqwAK4OW8OVx-2BpZA-2FERJY4Dn2CQe17ZXQoEWkt7zS7If2o196VJaX40JLqZh2vcC-2Bk-3D\"></span><span "
				+ "          style=\"color: black; font-size: 12pt;\"> "
				+ "          <o:p></o:p> "
				+ "        </span></p> "
				+ "    </div> "
				+ "    <div> "
				+ "      <div> "
				+ "        <div align=\"center\"> "
				+ "          <table width=\"100%\" class=\"MsoNormalTable\" id=\"x_x_bodyTable\" "
				+ "            style=\"background: rgb(228, 228, 228); width: 100%; border-collapse: collapse;\" border=\"0\" cellspacing=\"0\" "
				+ "            cellpadding=\"0\"> "
				 
				+ "            <tr> "
				+ "              <td width=\"100%\" id=\"x_x_bodyCell\" valign=\"top\" style=\"padding: 0cm; width: 100%;\"> "
				+ "                <div align=\"center\"> "
				+ "                  <table width=\"100%\" class=\"MsoNormalTable\" style=\"width: 100%; border-collapse: collapse;\" border=\"0\" "
				+ "                    cellspacing=\"0\" cellpadding=\"0\"> "
				 
				+ "                    <tr> "
				+ "                      <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                        <div align=\"center\"> "
				+ "                          <table class=\"MsoNormalTable\" style=\"border-collapse: collapse; max-width: 450pt;\" border=\"0\" "
				+ "                            cellspacing=\"0\" cellpadding=\"0\"> "
				 
				+ "                            <tr> "
				+ "                              <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                                <table width=\"600\" align=\"left\" class=\"MsoNormalTable\" "
				+ "                                  style=\"width: 450pt; border-collapse: collapse;\" border=\"0\" cellspacing=\"0\" "
				+ "                                  cellpadding=\"0\"> "
				 
				+ "                                  <tr> "
				+ "                                    <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                                      <table class=\"MsoNormalTable\" style=\"border-collapse: collapse; max-width: 450pt;\" "
				+ "                                        border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
				 
				+ "                                        <tr> "
				+ "                                          <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                                            <table width=\"390\" align=\"left\" class=\"MsoNormalTable\" "
				+ "                                              style=\"width: 292.5pt; border-collapse: collapse;\" border=\"0\" "
				+ "                                              cellspacing=\"0\" cellpadding=\"0\"> "
				 
				+ "                                              <tr> "
				+ "                                                <td valign=\"top\" style=\"padding: 6.75pt;\"></td> "
				+ "                                              </tr> "
				+ "                                            </table> "
				+ "                                            <p class=\"MsoNormal\"><span style=\"display: none;\"> "
				+ "                                                <o:p>&nbsp;</o:p> "
				+ "                                              </span></p> "
				+ "                                            <table width=\"210\" align=\"left\" class=\"MsoNormalTable\" "
				+ "                                              style=\"width: 157.5pt; border-collapse: collapse;\" border=\"0\" "
				+ "                                              cellspacing=\"0\" cellpadding=\"0\"> "
				 
				+ "                                              <tr> "
				+ "                                                <td valign=\"top\" style=\"padding: 6.75pt;\"> "
				+ "                                                  <p class=\"MsoNormal\" style=\"line-height: 120%;\"> "
				+ "                                                    <span "
				+ "                                                      style='color: rgb(136, 136, 136); line-height: 120%; font-family: \"Helvetica\",sans-serif; font-size: 8.5pt;'>Visualizar "
				+ "                                                      como "
				+ "                                                      <a href=\"https://eur02.safelinks.protection.outlook.com/?url=https%3A%2F%2Ft.rdsv1.net%2Fls%2Fclick%3Fupn%3DhyI6lZeK4ApKIY8yPVW5Z-2FArIglf-2BctprQMFV6CK9fvetX-2FZw-2FCnyr20HKgZ5CwyukBXr-2BsHMKVDMEOsQqDE8awfSI392bk5-2FgYUEVk5aPZ215gqZPp9MX5Yq9uo7kCDuZAZHhqitsoQ-2Fn9oyEkKVZOy-2FLR70fs4x9C27o97veKwye7f08MPxLfuQVVjdP-2BZMkV4fjGo6bZ0EAOJLV43ajUe4yj0HWOsRtGJe9rIeYg-3DW08M_AjPdJyTJzntoU5fymW-2Blq0u12e9-2BjYZ2WpfhJmI1KaP5iwYhrWeoCpzsM0a54Iar5Fh14CUIgS4tmFzmbuyECyxD91IGLVc7vX898-2B8mFSD-2FYrFX-2FeCQsBnCv1s0Uj1KzgHLyJWdZJpDsl2BnO6-2FXNQfgkI3BhTVOFfH4PNZTX0W-2B7rIyR1qpr3H4LB29nV4PJYDtv-2FoHbkFgUGxVcdACy7vwgYaOU63AxUiZ4uH16g4d2TJfoWk9h6fGeDn-2FK-2B-2FOLABYH4amrWrna82u0T9-2BvWrv56REOv6G0VOsQWoGK6dJKo6B0XX-2BIiUW-2Be9F5c3Dh8wnCB23-2F7bwPTy4LVcxeE9-2FoJxj1J-2Ff-2BZ9GHCee746pLDGwRnXQ1qrczGd0aOpBOG0xc6vw2tShOuWLaV4CPOKsutBN1G4EWlN4zrP6miHEpvEcJQZYWbuAt9XBeTDM3XZXyBmyM1yrGdGTfQqJ9AuZcsGZUXXvtpX8TMYEt47vRnL9YtMQwc2vVRVwJYdgDeCvj4RQC-2BJiGjy-2FMYoUnCZo2-2FdXpg-2Bays6E-2FdWepi6thXkO13MU1GyxfAnpAtG09LmT0wvPBzIzbLhWrDra7VxpkYQISg2946AMewc7PKcW5GTphg3W9X5KEMHIgGAmJr3dOEJjzWmtwipLNw-2FnqVg0TMiFWLrDaptRBDJKvU-3D&data=05%7C01%7Ceugenio.barbaresco%40seidor.com.br%7C8b6c049dc5b2461fae1608db41acc7e9%7C654623d615044f22a146b7c72637766a%7C0%7C0%7C638175982563859038%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=fET%2BXxCosgWvlu5rvlJQZsbhqRUdBZVJjmqoiH%2Fbxt8%3D&reserved=0\" "
				+ "                                                        target=\"loopstyle_link\" "
				+ "                                                        originalsrc=\"https://t.rdsv1.net/ls/click?upn=hyI6lZeK4ApKIY8yPVW5Z-2FArIglf-2BctprQMFV6CK9fvetX-2FZw-2FCnyr20HKgZ5CwyukBXr-2BsHMKVDMEOsQqDE8awfSI392bk5-2FgYUEVk5aPZ215gqZPp9MX5Yq9uo7kCDuZAZHhqitsoQ-2Fn9oyEkKVZOy-2FLR70fs4x9C27o97veKwye7f08MPxLfuQVVjdP-2BZMkV4fjGo6bZ0EAOJLV43ajUe4yj0HWOsRtGJe9rIeYg-3DW08M_AjPdJyTJzntoU5fymW-2Blq0u12e9-2BjYZ2WpfhJmI1KaP5iwYhrWeoCpzsM0a54Iar5Fh14CUIgS4tmFzmbuyECyxD91IGLVc7vX898-2B8mFSD-2FYrFX-2FeCQsBnCv1s0Uj1KzgHLyJWdZJpDsl2BnO6-2FXNQfgkI3BhTVOFfH4PNZTX0W-2B7rIyR1qpr3H4LB29nV4PJYDtv-2FoHbkFgUGxVcdACy7vwgYaOU63AxUiZ4uH16g4d2TJfoWk9h6fGeDn-2FK-2B-2FOLABYH4amrWrna82u0T9-2BvWrv56REOv6G0VOsQWoGK6dJKo6B0XX-2BIiUW-2Be9F5c3Dh8wnCB23-2F7bwPTy4LVcxeE9-2FoJxj1J-2Ff-2BZ9GHCee746pLDGwRnXQ1qrczGd0aOpBOG0xc6vw2tShOuWLaV4CPOKsutBN1G4EWlN4zrP6miHEpvEcJQZYWbuAt9XBeTDM3XZXyBmyM1yrGdGTfQqJ9AuZcsGZUXXvtpX8TMYEt47vRnL9YtMQwc2vVRVwJYdgDeCvj4RQC-2BJiGjy-2FMYoUnCZo2-2FdXpg-2Bays6E-2FdWepi6thXkO13MU1GyxfAnpAtG09LmT0wvPBzIzbLhWrDra7VxpkYQISg2946AMewc7PKcW5GTphg3W9X5KEMHIgGAmJr3dOEJjzWmtwipLNw-2FnqVg0TMiFWLrDaptRBDJKvU-3D\" "
				+ "                                                        shash=\"MgA2N6txyEcROXhvIMiaj2gugGzm9hmMuGTxyKrMZRvd5qMadXjgRnDoBm0eQHr3nC797BCs37VXpV+EPeOgfl+GTCVV0vDpSJttydavyOB7buOYLvrLCZ7T944K5g+T9qEMFrdY8HBeGW3d29HD2lG/X2M2Oo/NroB/NbtRnw0=\"> "
				+ "                                                        <span style=\"color: rgb(51, 51, 51);\">página web</span> "
				+ "                                                      </a> "
				+ "                                                      <o:p></o:p> "
				+ "                                                    </span> "
				+ "                                                  </p> "
				+ "                                                </td> "
				+ "                                              </tr> "
				+ "                                            </table> "
				+ "                                          </td> "
				+ "                                        </tr> "
				+ "                                      </table> "
				+ "                                    </td> "
				+ "                                  </tr> "
				+ "                                </table> "
				+ "                              </td> "
				+ "                            </tr> "
				+ "                          </table> "
				+ "                        </div> "
				+ "                      </td> "
				+ "                    </tr> "
				+ "                    <tr> "
				+ "                      <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                        <div align=\"center\"> "
				+ "                          <table class=\"MsoNormalTable\" "
				+ "                            style=\"background: white; border-collapse: collapse !important; max-width: 450pt;\" "
				+ "                            border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
				 
				+ "                            <tr> "
				+ "                              <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                                <table width=\"600\" align=\"left\" class=\"MsoNormalTable\" "
				+ "                                  style=\"width: 450pt; border-collapse: collapse;\" border=\"0\" cellspacing=\"0\" "
				+ "                                  cellpadding=\"0\"> "
				 
				+ "                                  <tr> "
				+ "                                    <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                                      <table width=\"100%\" class=\"MsoNormalTable\" "
				+ "                                        style=\"width: 100%; border-collapse: collapse;\" border=\"0\" cellspacing=\"0\" "
				+ "                                        cellpadding=\"0\"> "
				 
				+ "                                        <tr> "
				+ "                                          <td style=\"padding: 0cm;\"> "
				+ "                                            <p align=\"center\" class=\"MsoNormal\" style=\"text-align: center;\"><a "
				+ "                                                title='\"\" ' "
				+ "                                                href=\"https://eur02.safelinks.protection.outlook.com/?url=https%3A%2F%2Ft.rdsv1.net%2Fls%2Fclick%3Fupn%3DzmhshkAakmOBlinDXX4v6RYKXkHzsuAu-2BY8KD-2B1ztO02G2ZLUy9OleFoy-2FcIuiOdPux67cJ35oSmNpyNeHBfZ89tvrGIVCrOsT5aP980aUbQwSjghtNnZUVe7bMWrr-2B6HDFnVJULbbb3PjbZCB6OmMA4n2MVHwlXPEXTge8kcMBNBMKL66k7IBqleWMdzlWTay5W_AjPdJyTJzntoU5fymW-2Blq0u12e9-2BjYZ2WpfhJmI1KaP5iwYhrWeoCpzsM0a54Iar5Fh14CUIgS4tmFzmbuyECyxD91IGLVc7vX898-2B8mFSD-2FYrFX-2FeCQsBnCv1s0Uj1KzgHLyJWdZJpDsl2BnO6-2FXNQfgkI3BhTVOFfH4PNZTX0W-2B7rIyR1qpr3H4LB29nV4PJYDtv-2FoHbkFgUGxVcdACy7vwgYaOU63AxUiZ4uH16g4d2TJfoWk9h6fGeDn-2FK-2B-2FOLABYH4amrWrna82u0T9-2BvWrv56REOv6G0VOsQWoGK6dJKo6B0XX-2BIiUW-2Be9F5c3Dh8wnCB23-2F7bwPTy4LVcxeE9-2FoJxj1J-2Ff-2BZ9GHCee746pLDGwRnXQ1qrczGd0aOpBOG0xc6vw2tShOuWLaV4CPOKsutBN1G4EWlN4zrP6miHEpvEcJQZYWbuAt9XBeTDM3XZXyBmyM1yrGdGTfQqJ9AuZcsGZUXXvtpX8TMYEt47vRnL9YtMQwc2vVRVwJYdgDeCvj4RQC-2BJiGjy-2FMYoUt33iJnjZhHZy8stoJkxbnJMn7P9z3dyNQTSDawou3WAjcMm1eNwn3YTgMZhemhhTDoRcT5SJSpL6AtESfQ6WzlfIXFtAoOVSTjcWCg3XFBMaeYgMGD5Rc4BbnC84Dnvi-2FdJH6c0URTODnxkyTUerTM-3D&data=05%7C01%7Ceugenio.barbaresco%40seidor.com.br%7C8b6c049dc5b2461fae1608db41acc7e9%7C654623d615044f22a146b7c72637766a%7C0%7C0%7C638175982563859038%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=Kt2Ppi3CsQ7ryzBHKzzs5wOa18Ms%2FiyfRqTJ9ZyQ%2B00%3D&reserved=0\" "
				+ "                                                originalsrc=\"https://t.rdsv1.net/ls/click?upn=zmhshkAakmOBlinDXX4v6RYKXkHzsuAu-2BY8KD-2B1ztO02G2ZLUy9OleFoy-2FcIuiOdPux67cJ35oSmNpyNeHBfZ89tvrGIVCrOsT5aP980aUbQwSjghtNnZUVe7bMWrr-2B6HDFnVJULbbb3PjbZCB6OmMA4n2MVHwlXPEXTge8kcMBNBMKL66k7IBqleWMdzlWTay5W_AjPdJyTJzntoU5fymW-2Blq0u12e9-2BjYZ2WpfhJmI1KaP5iwYhrWeoCpzsM0a54Iar5Fh14CUIgS4tmFzmbuyECyxD91IGLVc7vX898-2B8mFSD-2FYrFX-2FeCQsBnCv1s0Uj1KzgHLyJWdZJpDsl2BnO6-2FXNQfgkI3BhTVOFfH4PNZTX0W-2B7rIyR1qpr3H4LB29nV4PJYDtv-2FoHbkFgUGxVcdACy7vwgYaOU63AxUiZ4uH16g4d2TJfoWk9h6fGeDn-2FK-2B-2FOLABYH4amrWrna82u0T9-2BvWrv56REOv6G0VOsQWoGK6dJKo6B0XX-2BIiUW-2Be9F5c3Dh8wnCB23-2F7bwPTy4LVcxeE9-2FoJxj1J-2Ff-2BZ9GHCee746pLDGwRnXQ1qrczGd0aOpBOG0xc6vw2tShOuWLaV4CPOKsutBN1G4EWlN4zrP6miHEpvEcJQZYWbuAt9XBeTDM3XZXyBmyM1yrGdGTfQqJ9AuZcsGZUXXvtpX8TMYEt47vRnL9YtMQwc2vVRVwJYdgDeCvj4RQC-2BJiGjy-2FMYoUt33iJnjZhHZy8stoJkxbnJMn7P9z3dyNQTSDawou3WAjcMm1eNwn3YTgMZhemhhTDoRcT5SJSpL6AtESfQ6WzlfIXFtAoOVSTjcWCg3XFBMaeYgMGD5Rc4BbnC84Dnvi-2FdJH6c0URTODnxkyTUerTM-3D\" "
				+ "                                                shash=\"z3naOlMVYKrIwss9G+gLUgp3xWfTXkja/kAFEFvgk7V9nhs8BCulJWNR0A3PltsOdGC39M9mzpGkW3G2BiB2ss4PRej4123f9EiEmEHw2mKINtjL/oJ6NEyrolgOxDlfzJ6YvSeQKM41r1E1YI2jwhjF9dSV0kzr1MSyYxtknXM=\"><span "
				+ "                                                  style=\"text-decoration: none;\"><img width=\"600\" height=\"200\" "
				+ "                                                    id=\"_x0000_i1025\" style=\"width: 6.25in; height: 2.083in;\" "
				+ "                                                    alt=\"MoveUP by Seidor - Migração Otimizada\" "
				+ "                                                    src=\"https://d335luupugsy2.cloudfront.net/cms/files/35910/1680551350/$4n8zswls6sb\" "
				+ "                                                    border=\"0\"></span></a> "
				+ "                                              <o:p></o:p> "
				+ "                                            </p> "
				+ "                                          </td> "
				+ "                                        </tr> "
				+ "                                      </table> "
				+ "                                      <p class=\"MsoNormal\" style=\"line-height: 110%;\"><span "
				+ "                                          style='color: rgb(102, 102, 102); line-height: 110%; font-family: \"Helvetica\",sans-serif; display: none;'> "
				+ "                                          <o:p> "
				+ "                                            <font size=\"3\">&nbsp;</font> "
				+ "                                          </o:p> "
				+ "                                        </span> "
				+ "                                      </p> "
				+ "                                      <table width=\"100%\" class=\"MsoNormalTable\" "
				+ "                                        style=\"width: 100%; border-collapse: collapse !important;\" border=\"0\" "
				+ "                                        cellspacing=\"0\" cellpadding=\"0\"> "
				 
				+ "                                        <tr> "
				+ "                                          <td style=\"padding: 6.75pt 13.5pt;\"> "
				+ "                                            <span "
				+ "                                              style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; font-size: 12pt; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                              <span> "
				+ "                                                <span class=\"ui-provider gp b c d e f g h i j k l m n o p q r s t u v w x y z ab ac ae af ag ah ai aj ak\" dir=\"ltr\"><span lang=\"PT-BR\"> "
				+ "                                                  <span lang=\"PT-BR\"> "
//				+ "                                                      <p align=\"LEFT\" dir=\"LTR\">Report de conclusão da Mudança realizada no dia @{triggerOutputs()?['body/Data_x0020_de_x0020_Execu_x00e7_']}. "
				+ "                                                      <p align=\"LEFT\" dir=\"LTR\">" + textoSaldacao + " <br><br> "
				 
                +                                                        textoCorpo
				
				+ "                                                      <p align=\"LEFT\" dir=\"LTR\">Em caso de dúvida entre em contato as equipes Seidor.</p> "
				+ "                                                      <p align=\"LEFT\" dir=\"LTR\"><br> Atenciosamente,<br><br></p> "
				
				+ "                                                  </span> "
				 
				+ "                                                    <font face=\"Calibri\" size=\"3\"> "
				+ "                                                      <font face=\"Calibri\" size=\"3\"> "
				+ "                                                        <p align=\"LEFT\" dir=\"LTR\"></p> "
				+ "                                                      </font> "
				+ "                                                    </font> "
				+ "                                                  </span> "
				 
				+ "                                                  <span lang=\"PT-BR\"> "
				+ "                                                    <p align=\"LEFT\" dir=\"LTR\"><strong>Equipe Seidor Cloud Services</strong></p> "
				+ "                                                    <p style=\"margin-bottom: 0px; margin-left: 0px;\"></p> "
				+ "                                                  </span> "
				 
				+ "                                                  <font face=\"Calibri\"> "
				+ "                                                    <font face=\"Calibri\"> "
				+ "                                                      <p align=\"LEFT\" dir=\"LTR\"></p> "
				+ "                                                    </font> "
				+ "                                                  </font> "
				 
				+ "                                                </span> "
				 
				+ "                                                <p style=\"line-height: 150%; margin-right: 0cm; margin-bottom: 7.5pt; margin-left: 0cm; mso-margin-top-alt: 7.5pt;\"> "
				+ "                                                  <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                    <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                      <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                        <span class=\"ui-provider gp b c d e f g h i j k l m n o p q r s t u v w x y z ab ac ae af ag ah ai aj ak\" dir=\"ltr\"> "
				+ "                                                          <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                            <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                              <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                                <span class=\"ui-provider gp b c d e f g h i j k l m n o p q r s t u v w x y z ab ac ae af ag ah ai aj ak\" dir=\"ltr\"> "
				+ "                                                                  <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                                    <span style='color: rgb(118, 113, 113); line-height: 150%; font-family: \"Helvetica\",sans-serif; mso-style-textfill-fill-color: #767171; mso-style-textfill-fill-alpha: 100.0%;'> "
				+ "                                                                      <span class=\"ui-provider gp b c d e f g h i j k l m n o p q r s t u v w x y z ab ac ae af ag ah ai aj ak\" dir=\"ltr\"><br> "
				+ "                                                                      </span> "
				+ "                                                                    </span> "
				+ "                                                                  </span> "
				+ "                                                                </span> "
				+ "                                                              </span> "
				+ "                                                            </span> "
				+ "                                                          </span> "
				+ "                                                        </span> "
				+ "                                                      </span> "
				+ "                                                    </span> "
				+ "                                                   </span> "
				+ "                                                </p> "
				+ "                                            </span> "
				+ "                                           </span> "
				+ "                                          </td> "
				+ "                                        </tr> "
				+ "                                      </table> "
				+ "                                    </td> "
				+ "                                  </tr> "
				+ "                                </table> "
				+ "                              </td> "
				+ "                            </tr> "
				+ "                          </table> "
				+ "                        </div> "
				+ "                      </td> "
				+ "                    </tr> "
				+ "                    <tr> "
				+ "                      <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                        <div align=\"center\"> "
				+ "                          <table class=\"MsoNormalTable\" style=\"border-collapse: collapse; max-width: 450pt;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
				+ "                            <tr> "
				+ "                              <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                                <table width=\"600\" align=\"left\" class=\"MsoNormalTable\" style=\"width: 450pt; border-collapse: collapse;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
				+ "                                  <tr> "
				+ "                                    <td valign=\"top\" style=\"padding: 0cm;\"> "
				+ "                                      <table width=\"600\" align=\"left\" class=\"MsoNormalTable\" style=\"width: 450pt; border-collapse: collapse;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
				+ "                                        <tr> "
				+ "                                          <td valign=\"top\" style=\"padding: 6.75pt;\"> "
				+ "                                            <p align=\"center\" style=\"text-align: center; line-height: 120%;\"> "
				+ "                                              <span "
				+ "                                                style='color: rgb(136, 136, 136); line-height: 120%; font-family: \"Helvetica\",sans-serif;'> "
				+ "                                                <font size=\"1\"> "
				+ "                                                  <font size=\"2\"> "
				+ "                                                    E-mail informativo enviado por <b>Seidor</b></font> "
				+ "                                                </font> "
				+ "                                              </span> "
				+ "                                            </p> "
				+ "                                            <p align=\"center\" style=\"text-align: center; line-height: 120%;\"><span "
				+ "                                                style='color: rgb(136, 136, 136); line-height: 120%; font-family: \"Helvetica\",sans-serif;'> "
				+ "                                                <font size=\"2\"> "
				+ "                                                  São Paulo | Belo Horizonte | Rio de Janeiro | Vitória | Porto Alegre | "
				+ "                                                  Florianópolis | Ribeirão Preto | Goiânia | Recife "
				+ "                                                </font> "
				+ "                                              </span> "
				+ "                                            </p> "
				+ "                                            <font size=\"2\"> "
				+ "                                            </font> "
				+ "                                            <p align=\"center\" style=\"text-align: center; line-height: 120%;\"> "
				+ "                                              <span "
				+ "                                                style='color: rgb(136, 136, 136); line-height: 120%; font-family: \"Helvetica\",sans-serif;'> "
				+ "                                                <font size=\"2\">Se deseja não receber mais mensagens como esta, </font> "
				+ "                                                <a "
				+ "                                                  href=\"https://eur02.safelinks.protection.outlook.com/?url=https%3A%2F%2Ft.rdsv1.net%2Fls%2Fclick%3Fupn%3DhyI6lZeK4ApKIY8yPVW5Z-2FArIglf-2BctprQMFV6CK9ftN91I2mnzNDfGYZsdPU2sWINpM0c7w4x-2B4v7CIIp1-2BcKC1WufM-2Be7-2FKfVX226MOSpm-2BWmZIvvlqRwQQMcAkB0Oue3KyfyUnBEoHAVktNGkwZjFzAkTIU011bycgHfWpyaw1qsCnGzsYOi3hwIPcrE4DGXE5cYmsr2hclSE01qr8J0fNIfIjn-2FSxTHo1cNjnl8-3Ddeu-_AjPdJyTJzntoU5fymW-2Blq0u12e9-2BjYZ2WpfhJmI1KaP5iwYhrWeoCpzsM0a54Iar5Fh14CUIgS4tmFzmbuyECyxD91IGLVc7vX898-2B8mFSD-2FYrFX-2FeCQsBnCv1s0Uj1KzgHLyJWdZJpDsl2BnO6-2FXNQfgkI3BhTVOFfH4PNZTX0W-2B7rIyR1qpr3H4LB29nV4PJYDtv-2FoHbkFgUGxVcdACy7vwgYaOU63AxUiZ4uH16g4d2TJfoWk9h6fGeDn-2FK-2B-2FOLABYH4amrWrna82u0T9-2BvWrv56REOv6G0VOsQWoGK6dJKo6B0XX-2BIiUW-2Be9F5c3Dh8wnCB23-2F7bwPTy4LVcxeE9-2FoJxj1J-2Ff-2BZ9GHCee746pLDGwRnXQ1qrczGd0aOpBOG0xc6vw2tShOuWLaV4CPOKsutBN1G4EWlN4zrP6miHEpvEcJQZYWbuAt9XBeTDM3XZXyBmyM1yrGdGTfQqJ9AuZcsGZUXXvtpX8TMYEt47vRnL9YtMQwc2vVRVwJYdgDeCvj4RQC-2BJiGjy-2FMYoUm1icIgUiBzU74qJLDqgnylNFG4ildTN-2BF4RKPFky1JmdUk8PWrrrvGT6xqb-2Fpo9iPsdtIOfY8gT6XWoq1iVdqjeqcv-2BOrH2UOVbp7mSUOtbe4bq5byfGdSyhE40LsP9r0QLejz0tCIAHojxKJGMpPc-3D&data=05%7C01%7Ceugenio.barbaresco%40seidor.com.br%7C8b6c049dc5b2461fae1608db41acc7e9%7C654623d615044f22a146b7c72637766a%7C0%7C0%7C638175982563859038%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=uO63h5JKh8JZY1qEqUnlEmwyp3%2FKQk%2BcgRaXHSBBw6s%3D&reserved=0\" "
				+ "                                                  target=\"loopstyle_link\" "
				+ "                                                  originalsrc=\"https://t.rdsv1.net/ls/click?upn=hyI6lZeK4ApKIY8yPVW5Z-2FArIglf-2BctprQMFV6CK9ftN91I2mnzNDfGYZsdPU2sWINpM0c7w4x-2B4v7CIIp1-2BcKC1WufM-2Be7-2FKfVX226MOSpm-2BWmZIvvlqRwQQMcAkB0Oue3KyfyUnBEoHAVktNGkwZjFzAkTIU011bycgHfWpyaw1qsCnGzsYOi3hwIPcrE4DGXE5cYmsr2hclSE01qr8J0fNIfIjn-2FSxTHo1cNjnl8-3Ddeu-_AjPdJyTJzntoU5fymW-2Blq0u12e9-2BjYZ2WpfhJmI1KaP5iwYhrWeoCpzsM0a54Iar5Fh14CUIgS4tmFzmbuyECyxD91IGLVc7vX898-2B8mFSD-2FYrFX-2FeCQsBnCv1s0Uj1KzgHLyJWdZJpDsl2BnO6-2FXNQfgkI3BhTVOFfH4PNZTX0W-2B7rIyR1qpr3H4LB29nV4PJYDtv-2FoHbkFgUGxVcdACy7vwgYaOU63AxUiZ4uH16g4d2TJfoWk9h6fGeDn-2FK-2B-2FOLABYH4amrWrna82u0T9-2BvWrv56REOv6G0VOsQWoGK6dJKo6B0XX-2BIiUW-2Be9F5c3Dh8wnCB23-2F7bwPTy4LVcxeE9-2FoJxj1J-2Ff-2BZ9GHCee746pLDGwRnXQ1qrczGd0aOpBOG0xc6vw2tShOuWLaV4CPOKsutBN1G4EWlN4zrP6miHEpvEcJQZYWbuAt9XBeTDM3XZXyBmyM1yrGdGTfQqJ9AuZcsGZUXXvtpX8TMYEt47vRnL9YtMQwc2vVRVwJYdgDeCvj4RQC-2BJiGjy-2FMYoUm1icIgUiBzU74qJLDqgnylNFG4ildTN-2BF4RKPFky1JmdUk8PWrrrvGT6xqb-2Fpo9iPsdtIOfY8gT6XWoq1iVdqjeqcv-2BOrH2UOVbp7mSUOtbe4bq5byfGdSyhE40LsP9r0QLejz0tCIAHojxKJGMpPc-3D\" "
				+ "                                                  shash=\"QfdW1cyTJ97AZT2hQj7TNcyf6QFDCtirM3+ehidk/yqd2yCcNWaGgInLjyVTsffiXADNSOOj0Cb1mNwgonLD7q5J8fpfs5s3cXRqjdF64qGnE1uuXTW3uhZh4tZcfBFb0mF6H2v5UA9T56ywo+sxegBTcNOpWBQ2PwA/jDPVLbc=\"><span "
				+ "                                                    style=\"color: rgb(51, 51, 51);\"> "
				+ "                                                    <font size=\"2\">clique aqui</font> "
				+ "                                                  </span> "
				+ "                                                </a> "
				+ "                                                <font size=\"2\">. </font> "
				+ "                                              </span> "
				+ "                                            </p> "
				+ "                                            <p align=\"center\" class=\"MsoNormal\" "
				+ "                                              style=\"text-align: center; line-height: 120%;\"> "
				+ "                                              <font size=\"3\"><span "
				+ "                                                  style='color: white; line-height: 120%; font-family: \"Helvetica\",sans-serif;'><br></span> "
				+ "                                              </font> "
				+ "                                            </p> "
				+ "                                          </td> "
				+ "                                        </tr> "
				+ "                                      </table> "
				+ "                                    </td> "
				+ "                                  </tr> "
				+ "                                </table> "
				+ "                              </td> "
				+ "                            </tr> "
				+ "                          </table> "
				+ "                        </div> "
				+ "                      </td> "
				+ "                    </tr> "
				+ "                  </table> "
				+ "                </div> "
				+ "              </td> "
				+ "            </tr> "
				+ "          </table> "
				+ "        </div> "
				+ "        <p class=\"MsoNormal\" style=\"background: rgb(228, 228, 228);\"><span "
				+ "            style=\"color: white; font-size: 1pt;\">•</span><span style=\"color: black;\"> </span><span "
				+ "            style=\"font-size: 1pt;\"> "
				+ "            <o:p></o:p> "
				+ "          </span></p> "
				+ "      </div> "
				+ "    </div> "
				+ "  </div>";
		
		return testo;
	}

/*	
	public String getMensagemAbertura( String textoTitulo, String textoSaldacao, String textoCorpo ) {
	
		String saida = null;
		String texto_01_HTML = "<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "    <meta charset=\"UTF-8\">"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "    <title>Informativo Cloud - CMUD</title>"
				+ "</head>"
				+ "<body style=\"margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;\">"
				+ "    <table align=\"center\" border=\"0\" width=\"55%\" cellpaddin=\"0\" cellspacing=\"0\" style=\"padding: 10;\"> "
				+ "        <tr>"
				+ "            <td bgcolor=\"#fff\">"
				+ "                <!-- Header -->"
				+ "                <table align=\"center\" border=\"1\" witdh=\"600px\" cellpaddin=\"0\" cellspacing=\"0\" style=\"padding: 10;\">"
				+ "                   <tr>"
				+ "                    <td bgcolor=\"#001058\" align=\"center\" style=\"padding: 0px 0 0px 0\">"
				+ "                        <img src='cid:logoImage'/>"
				+ "                    </td>"
				+ "                   </tr>"
				+ "                   <!-- Corpo  -->"
				+ "                   <tr>"
				+ "                    <td>"
				+ "                        <table align=\"center\" border=\"0\" witdh=\"600px\" cellpaddin=\"0\" cellspacing=\"0\" style=\"padding: 10; color: #001058;\">"
				+ "                             <tr >"
				+ "                                <td>"
				+ "                                    <h1>";
				
		String texto_02_HTML =                       " </h1>"
				+ "                                </td>"
				+ "                             </tr>"
				+ "                        </table>"
				+ "                        <table border=\"0\" witdh=\"600px\" cellpaddin=\"0\" cellspacing=\"0\" style=\"padding: 10;\">"
				+ "                            <tr align=\"left\">"
				+ "                                <td>"
				+ "                                    <span>"
				+ "                                        <strong>Prezado,</strong> "
				+ "                                    </span>"
				+ "                                    <br><br><br>"
				+ "                                   <span>";
		
		String texto_03_HTML =  "                   </span>  <br><br><br>"
				+ "                                 <table>";
				
		String texto_04_HTML = "                    </table>"
				+ "                                   <br><br><br>"
				+ "                                   <span> "
				+ "                                        Em caso de dúvida entre em contato as equipes Seidor. "
				+ "                                    </span><br><br>  "
				+ "                                    <span> "
				+ "                                        Atenciosamente.<br> "
				+ "                                        <strong>Equipe Seidor Cloud Services</strong> "
				+ "                                    </span>  "
				+ "                                    <br><br><br> "
				+ "                                </td> "
				+ "                            </tr> "
				+ "                        </table> "
				+ "                    </td> "
				+ "                   </tr> "
				+ "                </table> "
				+ "            </td> "
				+ "        </tr> "
				+ "    </table> "
				+ "     "
				+ "</body> "
				+ "</html>";
		
		
		saida = texto_01_HTML + textoTitulo + texto_02_HTML + textoSaldacao + texto_03_HTML + textoCorpo + texto_04_HTML;
		
		return saida;
	}
	
	public String getTrTd(String[][] texto) {
		String s = "";		
		for(int i=0; i<texto.length; i++){
			s += "<tr>";
		   for(int j=0; j<texto[i].length; j++){
			   if( j == 0 ) {
				s+= "<td><strong>" + texto[i][j] + "</strong></td>";
			   } else {
				   s+= "<td>: " + texto[i][j] + "</td>";  
			   }
		 //     System.out.print("J: " + matriz[i][j] + "\n");
		   }
		   s += "</tr>";
//		   System.out.println("I: " + matriz[i][0]);
		}
		
		return s;
	}	
*/	
}
