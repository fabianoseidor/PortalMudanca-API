package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import br.com.portalmudanca.model.AtividadeMudanca;
import br.com.portalmudanca.model.dto.ListaTarefaPorResponDTO;

@Repository
@Transactional
public interface AtividadeMudancaRepository extends JpaRepository<AtividadeMudanca, Long>{

	@Query(value = "select s from AtividadeMudanca s where mudanca.id_mudanca = ?1 ORDER by id_atividade_mudanca")
	List<AtividadeMudanca> buscaListaAtitividades(Long idMudanca);
	
	@Query(value = "select s from AtividadeMudanca s where responsavelAtividade.id_responsavel_atividade = ?1 and dt_final_tarefa is null ORDER by mudanca.id_mudanca, id_atividade_mudanca")
	List<AtividadeMudanca> listaAtitividadesEmAberto( Long id );
	
	@Query(value = "select s from AtividadeMudanca s where id_atividade_mudanca = ?1")
	AtividadeMudanca buscaPorIdAtitividades(Long idtaividadeMudanca);
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET dt_inicio_tarefa = getdate(), id_status_atividade= 1 WHERE id_atividade_mudanca = ?1")
	void inicializaTarefa(Long id);		

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET dt_final_tarefa = getdate(), id_status_atividade = 3, report_final = ?2 WHERE id_atividade_mudanca = ?1")
	void finalizaTarefa(Long id, String reportFinal);	
	
	@Query(value = "SELECT COUNT( id_atividade_mudanca ) FROM atividade_mudanca WHERE id_mudanca = ?1 AND id_status_atividade <> 3", nativeQuery = true)
	Long qtyAtitividadesAbertas(Long idtaividadeMudanca);
	
	@Query(value = "SELECT CASE WHEN a.dt_inicio_tarefa IS NOT NULL THEN 'INICIADA' ELSE NULL END FROM atividade_mudanca a WHERE a.id_atividade_mudanca = ?1", nativeQuery = true)
	String verificaInicioAtividade(Long idtaividadeMudanca);
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET dt_inicio_tarefa = ?1, id_status_atividade= 1 WHERE id_atividade_mudanca = ?2")
	void inicializaTarefaAutomatica(LocalDateTime dt_inicio_tarefa, Long id);		

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET dt_final_tarefa = ?3, id_status_atividade = 3, report_final = ?2 WHERE id_atividade_mudanca = ?1")
	void finalizaTarefaAutomatica(Long id, String reportFinal, LocalDateTime dt_inicio_tarefa);	

	@Query(value = "select am2.prioridade from atividade_mudanca as am2 where am2.id_atividade_mudanca = ?1", nativeQuery = true)
	Integer getPrioridade(Long id);

	@Query(value = "select * from atividade_mudanca WHERE id_mudanca = ?1 and dt_final_tarefa is null ORDER by id_atividade_mudanca", nativeQuery = true)
	List<AtividadeMudanca> buscaListaAtitividadesAbetas(Long idMudanca);

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET id_responsavel_atividade = ?2 WHERE id_atividade_mudanca = ?1")
	void mudarRespAtividade(Long id, Long idResponsavel);		

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE atividade_mudanca SET dt_final_tarefa = getdate(), dt_inicio_tarefa = getdate(), id_status_atividade = 3, tarefa_ignorada = ?3, report_final = ?2 WHERE id_atividade_mudanca = ?1")
	void ignorarTarefa( Long id, String reportFinal, String tarefaIgnorada );	

	@Query(value = "select mu.id_tipo_mudanca from atividade_mudanca as am , mudanca as mu where am.id_atividade_mudanca = ?1 and mu.id_mudanca = am.id_mudanca", nativeQuery = true)
	Long getIdTipoMudanca( Long id );
		
	  @Query(value = "select new br.com.portalmudanca.model.dto.ListaTarefaPorResponDTO("
                   + "     mu.id_mudanca                                                "
                   + "   , mu.titulo_mudanca                                            "
                   + "   , mu.tipoMudanca.id_tipo_mudanca                               "
                   + "   , am.id_atividade_mudanca                                      "
                   + "   , am.titulo_atividade_mudanca                                  "
                   + "   , am.dt_tarefa                                                 "
                   + "   , am.duracao                                                   "
                   + "   , am.prioridade                                                "
                   + "   , am.atividade_mudanca                                         "
                   + "   , ra.id_responsavel_atividade                                  "
                   + "   , am.dt_inicio_tarefa                                          "
                   + "   , am.dt_final_tarefa                                           "
                   + "   , ra.responsavel_atividade                                     "
                   + "   , mu.login_user                                                "
                   + "   )                                                              "
                   + "  from                                                            "
                   + "       AtividadeMudanca        am                                 "
                   + "  JOIN am.mudanca              mu                                 "
                   + "  JOIN am.responsavelAtividade ra                                 "
                   + " where ra.id_responsavel_atividade = ?1                           "
                   + "   and am.dt_final_tarefa is null                                 "
                   + " order by mu.id_mudanca, am.id_atividade_mudanca                  ")
	List<ListaTarefaPorResponDTO> listaAtitividadesEmAbertoDTO( Long id );

	@Query(value = " select                                        "
		 	     + "    CASE                                       "
			     + "      WHEN  mu.id_tipo_mudanca = 3 THEN 1      "
			     + "      ELSE 0                                   "
			     + "    END AS check_tipo_mudanca                  "
			     + " from                                          "
			     + "    atividade_mudanca as am                    "
			     + "  , mudanca           as mu                    "
			     + " where am.id_atividade_mudanca = ?1            "
			     + "   and mu.id_mudanca           = am.id_mudanca ", nativeQuery = true)
	Integer checkTipoMudanca(Long id);
	  
	@Query(value = " select am.id_mudanca from atividade_mudanca as am where am.id_atividade_mudanca = ?1 ", nativeQuery = true)
    Long getIdMudancaAtividade(Long id);
	  
}
