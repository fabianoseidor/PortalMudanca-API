package br.com.portalmudanca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.portalmudanca.controller.AprovadoresController;
import br.com.portalmudanca.controller.CategoriaPadraoController;
import br.com.portalmudanca.controller.CriticidadeController;
import br.com.portalmudanca.controller.ImpactoMudancaController;
import br.com.portalmudanca.controller.ResponsavelAtividadeController;
import br.com.portalmudanca.controller.TipoArqController;
import br.com.portalmudanca.controller.TipoMudancaController;
import br.com.portalmudanca.model.Aprovadores;
import br.com.portalmudanca.model.CategoriaPadrao;
import br.com.portalmudanca.model.Criticidade;
import br.com.portalmudanca.model.ImpactoMudanca;
import br.com.portalmudanca.model.ResponsavelAtividade;
import br.com.portalmudanca.model.TipoArq;
import br.com.portalmudanca.model.TipoMudanca;

@Profile("test")
@SpringBootTest(classes = PortalMudancaApplication.class)
public class TesteInserts {
	
	@Autowired
	private AprovadoresController aprovadoresController;
	
	@Autowired
	private TipoMudancaController tipoMudancaController;
	
	@Autowired
	private CriticidadeController criticidadeController;
	
	@Autowired
	private ImpactoMudancaController impactoMudancaController;
	
	@Autowired
	private TipoArqController tipoArqController;
	
	@Autowired
	private CategoriaPadraoController categoriaPadraoController;
	
	@Autowired
	private ResponsavelAtividadeController responsavelAtividadeController;
	
	@Test
	public void testCadAprovadores() throws ExceptionCustomizada {
		
		Aprovadores aprovadores = new Aprovadores();
		
		aprovadores.setAprovador("Fabiano".toUpperCase());
		aprovadores.setObs("Teste gravacao".toUpperCase());
		
		aprovadoresController.salvarAprovadores(aprovadores);
	}

	@Test
	public void testCadTipoMudanca() throws ExceptionCustomizada {
		
		TipoMudanca tipoMudanca = new TipoMudanca();
		
		tipoMudanca.setTipo_mudanca("Padrão".toUpperCase());
		tipoMudanca.setObs("Teste gravacao".toUpperCase());
		
		tipoMudancaController.SalvarTipoMudanca(tipoMudanca);
	}
	
	
	@Test
	public void testCadCriticidade() throws ExceptionCustomizada {
		
		Criticidade criticidade = new Criticidade();
		
		criticidade.setCriticidade("Critica".toUpperCase());
		criticidade.setObs("Teste gravacao".toUpperCase());
		
		criticidadeController.SalvarCriticidade(criticidade);
	}

	@Test
	public void testCadImpactoMudanca() throws ExceptionCustomizada {
		
		ImpactoMudanca impactoMudanca = new ImpactoMudanca();
		
		impactoMudanca.setImpacto_mudanca("Baixa".toUpperCase());
		impactoMudanca.setObs("Teste gravacao".toUpperCase());
		
		impactoMudancaController.SalvarImpactoMudanca(impactoMudanca);
	}
	
	@Test
	public void testCadTipoArq() throws ExceptionCustomizada {
		
		TipoArq tipoArq = new TipoArq();
		
		tipoArq.setTipo_arq("xlsx".toUpperCase());
		
		tipoArqController.SalvarTipoArq(tipoArq);
	}

	@Test
	public void testCadCategoriaPadrao() throws ExceptionCustomizada {
		
		CategoriaPadrao categoriaPadrao = new CategoriaPadrao();
		
		categoriaPadrao.setCategoria_padrao("Expansão de Memória".toUpperCase());
		categoriaPadrao.setObs("Teste gravacao".toUpperCase());
		
		categoriaPadraoController.SalvarCategoriaPadrao(categoriaPadrao);
	}
	
	
	@Test
	public void testCadResponsavelAtividade() throws ExceptionCustomizada {
		
		ResponsavelAtividade responsavelAtividade = new ResponsavelAtividade();
		
		responsavelAtividade.setResponsavel_atividade("Erich".toUpperCase());
		
		responsavelAtividadeController.SalvarResponsavelAtividade(responsavelAtividade);
	}

	
}
