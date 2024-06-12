package br.com.portalmudanca.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portalmudanca.enums.StatusRdm;
import br.com.portalmudanca.model.ArquivosMudanca;
import br.com.portalmudanca.model.ExecutorCategoriaPadrao;
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.dto.ListaMudancaDTO;
import br.com.portalmudanca.model.dto.MudancaDTO;
import br.com.portalmudanca.model.dto.MudancaPadraoDTO;
import br.com.portalmudanca.repository.ArquivosMudancaRepository;
import br.com.portalmudanca.repository.ExecutorCategoriaPadraoRepository;
import br.com.portalmudanca.repository.MudancaRepository;


@Service
public class MudancaService {

	@Autowired
	private MudancaRepository mudancaRepository;
	
	@Autowired
	private ArquivosMudancaRepository arquivosMudancaRepository;
	
	@Autowired
	private ExecutorCategoriaPadraoRepository executorCategoriaPadraoRepository;
	
	public Mudanca saveMudancaNormal( Mudanca mudanca ) {		
		return mudancaRepository.saveAndFlush(mudanca);
	}

	public Boolean existByIdMudanca( Long idMudanca ) {
		
		return mudancaRepository.findByIdMudanca(idMudanca).getId_mudanca() > 0;
	}

	public Mudanca upadateStatusMudanca( Mudanca mudanca, StatusRdm status ) {	
		
		mudanca.setStatusMudanca(status);

		return mudancaRepository.save(mudanca);
	}
	
	public Mudanca findtByIdMudanca( Long idMudanca ) {
		
		return mudancaRepository.findByIdMudanca(idMudanca);
	}

	public MudancaDTO getMudancaDTO( Mudanca mudanca ) {
		MudancaDTO mudancaDTO = new MudancaDTO();
		
		mudancaDTO.setId_mudanca(mudanca.getId_mudanca());
		mudancaDTO.setTitulo_mudanca(mudanca.getTitulo_mudanca());
		mudancaDTO.setDt_criacao(mudanca.getDt_criacao());
		mudancaDTO.setDt_alteracao(mudanca.getDt_alteracao());
		mudancaDTO.setLogin_user(mudanca.getLogin_user());
		mudancaDTO.setStatusMudanca(mudanca.getStatusMudanca());
		return mudancaDTO;
	}
	
	public MudancaPadraoDTO getMudancaPadrao( Long id ) {
		
		MudancaPadraoDTO mudancaPadraoDTO = new MudancaPadraoDTO();
		
		Mudanca mudanca = findtByIdMudanca(id);
		
		mudancaPadraoDTO.setMudanca(mudanca);
		mudancaPadraoDTO.setTipoMudanca(mudanca.getTipoMudanca() );
		mudancaPadraoDTO.setImpactoMudanca(mudanca.getImpactoMudanca());
		mudancaPadraoDTO.setCriticidade(mudanca.getCriticidade());
		mudancaPadraoDTO.setCategoriaPadrao(mudanca.getCategoriaPadrao());
		mudancaPadraoDTO.setDadosMudanca(mudanca.getDadosMudanca());
		
		List<ArquivosMudanca> listaArquivosMudanca = new ArrayList<ArquivosMudanca>();
		listaArquivosMudanca = arquivosMudancaRepository.buscarArquivosMudancaPorIdMudanca(id);
		
		for( int i = 0; i < listaArquivosMudanca.size(); i++ )
			mudancaPadraoDTO.getArquivosMudancas().add(listaArquivosMudanca.get(i));
		
				
		List<ExecutorCategoriaPadrao> listaExecutorCategoriaPadrao = executorCategoriaPadraoRepository.buscaByCategoriaPorCategoria( mudanca.getCategoriaPadrao().getId_categoria_padrao() );
		
		for(ExecutorCategoriaPadrao execCatPadrao: listaExecutorCategoriaPadrao)
			mudancaPadraoDTO.getExecutorCategoriaPadraos().add(execCatPadrao);
				
		return mudancaPadraoDTO;
	}

	
	public List<ListaMudancaDTO> getListaMudancaPorTitulo( String titulo ) {
		
		List<Mudanca> listaMudanca = mudancaRepository.listaMudancaPorTitulo(titulo);
		List<ListaMudancaDTO> listaMudancaDTOs = new ArrayList<ListaMudancaDTO>();
		
		for(int i = 0; i < listaMudanca.size(); i++ ) {
			ListaMudancaDTO listaMudancaDTO = new ListaMudancaDTO();
			listaMudancaDTO.setId_mudanca    ( listaMudanca.get(i).getId_mudanca()                   );
			listaMudancaDTO.setTitulo_mudanca( listaMudanca.get(i).getTitulo_mudanca()               );
			listaMudancaDTO.setDt_criacao    ( listaMudanca.get(i).getDt_criacao()                   );
			listaMudancaDTO.setDt_alteracao  ( listaMudanca.get(i).getDt_alteracao()                 );
			listaMudancaDTO.setLogin_user    ( listaMudanca.get(i).getLogin_user()                   );
			listaMudancaDTO.setStatusMudanca ( listaMudanca.get(i).getStatusMudanca().getStatusRdm() );
			listaMudancaDTO.setTipoMudanca   ( listaMudanca.get(i).getTipoMudanca()                  );
			
			listaMudancaDTOs.add(listaMudancaDTO);
		}
		
		
		return listaMudancaDTOs;
	}

}
