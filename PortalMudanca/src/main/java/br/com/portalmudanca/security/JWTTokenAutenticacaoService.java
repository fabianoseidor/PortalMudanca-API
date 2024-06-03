package br.com.portalmudanca.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*Criar a autenticação e retonar também a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {
	/*Token de validade de 11 dias*/
	private static final long EXPIRATION_TIME = 959990000;
	
	/*Chave de senha para juntar com o JWT*/
	private static final String SECRET = "23lk42j03ejdwdk*&^%$kadlak";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	
	/* Gera o  */
	public void addAuthentication( HttpServletResponse response, String username ) throws Exception{
		
		/* Motagem do Token */
		String JWT = Jwts.builder().
				    setSubject(username)
				    .setExpiration(new Date( System.currentTimeMillis() + EXPIRATION_TIME ))
				    .signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		String token = TOKEN_PREFIX + " " + JWT;
		
		response.addHeader(HEADER_STRING, token);
		
		response.getWriter().write("{\"Authorization\" \"" + token + "\"}");
	}
}
