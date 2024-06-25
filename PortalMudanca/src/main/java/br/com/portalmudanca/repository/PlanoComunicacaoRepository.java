package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.Aprovadores;
import br.com.portalmudanca.model.PlanoComunicacao;
import br.com.portalmudanca.model.dto.PlanoComunicacaoDAO;

@Repository
@Transactional
public interface PlanoComunicacaoRepository extends JpaRepository<PlanoComunicacao, Long>{
	
	@Query(value = "SELECT a.id_plano_comunicacao, a.nome_contato, a.email, a.empresa, a.dt_criacao FROM plano_comunicacao a where id_plano_comunicacao = ?1", nativeQuery = true)
	PlanoComunicacaoDAO buscaIdPlanoComunicacao(Long idPlanoComunicacao);
	
	@Query(value = "SELECT a.* FROM plano_comunicacao a where upper(trim(empresa)) like %?1%", nativeQuery = true)
	List<PlanoComunicacao> findByEmpresa(String empresa);
	
	@Query(value = "SELECT a.* FROM plano_comunicacao a where upper(trim(nome_contato)) like %?1%", nativeQuery = true)
	List<PlanoComunicacao> findByNomeContato(String nome_contato);
	
	@Query(value = "SELECT a.* FROM plano_comunicacao a", nativeQuery = true)
	List<PlanoComunicacao> listaPlanoComunicacao();

	@Query(value = "SELECT a.* FROM plano_comunicacao a where upper(trim(empresa)) = ?1", nativeQuery = true)
	Aprovadores existPlanoComunicacao(String empresa);
	
	@Query(value = "SELECT a.* FROM plano_comunicacao a where upper(trim(nome_contato)) like %?1%", nativeQuery = true)
	List<PlanoComunicacao> findByContato(String contato);

	@Query(value = "SELECT a.* FROM plano_comunicacao a where id_plano_comunicacao = ?1", nativeQuery = true)
	PlanoComunicacao buscaPorIdPlanoComunicacao(Long idPlanoComunicacao);

}
