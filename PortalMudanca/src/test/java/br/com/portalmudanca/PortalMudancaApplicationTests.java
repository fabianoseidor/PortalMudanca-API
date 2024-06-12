package br.com.portalmudanca;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.portalmudanca.controller.ClientesAfetadosController;
import br.com.portalmudanca.model.ClientesAfetados;


@SpringBootTest(classes = PortalMudancaApplication.class)
public class PortalMudancaApplicationTests {

//	@Autowired
//	private ClientesAfetadosService clientesAfetadosService;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Test
	public void testRestApiCadastroClientesAfetados() throws JsonProcessingException, Exception {
		ClientesAfetados  cli = new ClientesAfetados();
		cli.setId_cliente_portal(1L);
		cli.setNome_cliente("Lucas Amaral");

	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.post("/salvarClientesAfetados")
	    						 .content(objectMapper.writeValueAsString(cli)) // Converte Objeto para JSON.
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
	    
	    // Converte JSON para Objeto.
	    ClientesAfetados objRetorno  = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), ClientesAfetados.class);
	    
	    // Valida se o objeto que enviou para sabar, foi salva com sucesso, comparando se o objeto enviado para ser salve eh o mesmo que foi retornado pelo JPA apos ser salvo.
	    assertEquals(cli.getNome_cliente(), objRetorno.getNome_cliente());
	}
	
	
	@Autowired
	private ClientesAfetadosController clientesAfetadosController;
	
	@Test
	public void contextLoads() throws ExceptionCustomizada {
		ClientesAfetados  cli = new ClientesAfetados();
		
		cli.setId_cliente_portal(2L);
		cli.setNome_cliente("Nana");
		clientesAfetadosController.salvarClientesAfetados(cli);
		
	}

}
