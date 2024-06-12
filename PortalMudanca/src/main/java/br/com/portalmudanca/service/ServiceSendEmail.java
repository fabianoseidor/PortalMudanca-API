package br.com.portalmudanca.service;

import java.io.UnsupportedEncodingException;
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
/*			
			MimeMessage message = new MimeMessage(session);
			message.setFrom      (new InternetAddress(email_from, titulo_email, "UTF-8"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject   (assunto  , "UTF-8");
			message.setText      (menssagem, "UTF-8");
			message.setSentDate  (new Date());
			
			Transport.send(message);
*/			
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email_from, titulo_email, "UTF-8"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			message.setContent(menssagem, "text/html; charset=utf-8");
			
			Transport.send(message);			
			
		}
		catch (MessagingException mex) {
			mex.printStackTrace();
		}
		
		
	}
	
	public String getMensagemAbertura( String textoSadacao, String textoCorpo ) {
		
		String basicPath = System.getProperty("user.dir");
		String PathImage = basicPath + "/src/main/resources/image/imagemFundo.jpg";
		
		String saida = null;
		String texto_01 = "<!DOCTYPE html>"
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
//				+ "                        <img src=\"C:/Deselvolvimento/Modelo Email/EmailCriacaoGmud/imagemFundo.jpg\" />"
				+ "                        <img src=" + PathImage + "/>"
				+ "                    </td>"
				+ "                   </tr>"
				+ "                   <!-- Corpo  -->"
				+ "                   <tr>"
				+ "                    <td>"
				+ "                        <table align=\"center\" border=\"0\" witdh=\"600px\" cellpaddin=\"0\" cellspacing=\"0\" style=\"padding: 10; color: #001058;\">"
				+ "                             <tr >"
				+ "                                <td>"
				+ "                                    <h1>Abertura de GMUD</h1>"
				+ "                                </td>"
				+ "                             </tr>"
				+ "                        </table>"
				+ "                        <table border=\"0\" witdh=\"600px\" cellpaddin=\"0\" cellspacing=\"0\" style=\"padding: 10;\">"
				+ "                            <tr align=\"left\">"
				+ "                                <td>"
				+ "                                    <span>"
				+ "                                        <strong>";
		
		String texto_02 = "</strong> "
				+ "                                    </span><br><br><br> "
				+ "                                    <span> ";
		
		String texto_03 = "</span><br><br><br> "
				+ " "
				+ "                                    <span> "
				+ "                                        Agradecemos pela sua atenção e colaboração neste assunto. "
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
		
		
		saida = texto_01 + textoSadacao + texto_02 + textoCorpo + texto_03;
		
		return saida;
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
