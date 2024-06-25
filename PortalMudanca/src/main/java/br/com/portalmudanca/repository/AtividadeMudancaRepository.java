package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.AtividadeMudanca;

@Repository
@Transactional
public interface AtividadeMudancaRepository extends JpaRepository<AtividadeMudanca, Long>{

	@Query(value = "select s from AtividadeMudanca s where mudanca.id_mudanca = ?1")
	List<AtividadeMudanca> buscaListaAtitividades(Long idMudanca);
	
	@Query(value = "select s from AtividadeMudanca s where id_atividade_mudanca = ?1")
	AtividadeMudanca buscaPorIdAtitividades(Long idtaividadeMudanca);
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET dt_inicio_tarefa = getdate(), id_status_atividade= 1 WHERE id_atividade_mudanca = ?1")
	void inicializaTarefa(Long id);		

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET dt_final_tarefa = getdate(), id_status_atividade = 3, report_final = ?2 WHERE id_atividade_mudanca = ?1")
	void finalizaTarefa(Long id, String reportFinal);	
	
	@Query(value = "SELECT COUNT( id_atividade_mudanca ) FROM atividade_mudanca WHERE id_mudanca = 38 AND id_status_atividade <> 3", nativeQuery = true)
	Long qtyAtitividadesAbertas(Long idtaividadeMudanca);
	
	
	
}
