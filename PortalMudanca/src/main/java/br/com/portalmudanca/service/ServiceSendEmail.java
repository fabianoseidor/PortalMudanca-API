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

@Service
public class ServiceSendEmail {

	 
	 
	final String userName     = "emailappsmtp.39c07be65b2db29e"; //requires valid gmail id
	final String password     = "widv2sWEwYC6";                  // correct password for gmail id
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
	
	
/*
 //**********************************************************
 //* Exemplo de declaracao e chamada do envio de um e-mail. *
 //**********************************************************
 
 	// Declaracao do objeto
 	@Autowired
	private ServiceSendEmail serviceSendEmail = new ServiceSendEmail();

    // Chamada do Objeto para envio do e-mail   
	try {                              //  
		serviceSendEmail.enviarEmailHtml("Erro na GMUD", "Teste Envio E-mail",  "fabiano.bolacha@gmail.com,amaralanalysissoftware@gmail.com,fabiano.amaral@seidor.com");
		
	} catch (UnsupportedEncodingException | MessagingException e) {
		e.printStackTrace();
	}

========================
           
 //**********************************************************
 //* Exemplo para gerar o corpo do e-mail em HTML.          *
 //**********************************************************

	StringBuilder menssagemHtml = new StringBuilder();
	
	menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
	menssagemHtml.append("<b>Login: </b>"+pessoaFisica.getEmail()+"<br/>");
	menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
	menssagemHtml.append("Obrigado!");
	
	try {
	  serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", menssagemHtml.toString() , pessoaFisica.getEmail());
	}catch (Exception e) {
		e.printStackTrace();
	}

 
 */
	
	
}
