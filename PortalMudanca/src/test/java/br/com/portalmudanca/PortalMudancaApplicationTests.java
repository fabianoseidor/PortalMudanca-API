package br.com.portalmudanca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.portalmudanca.controller.ClientesAfetadosController;
import br.com.portalmudanca.model.ClientesAfetados;


@SpringBootTest(classes = PortalMudancaApplication.class)
public class PortalMudancaApplicationTests {

//	@Autowired
//	private ClientesAfetadosService clientesAfetadosService;
	
	@Autowired
	private ClientesAfetadosController clientesAfetadosController;
	
	@Test
	public void contextLoads() {
		ClientesAfetados  cli = new ClientesAfetados();
		
		cli.setId_cliente_portal(2L);
		cli.setNome_cliente("Nana");
		clientesAfetadosController.salvarClientesAfetados(cli);
		
	}

}
