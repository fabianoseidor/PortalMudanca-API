package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import br.com.portalmudanca.model.HistoricoTransferenciaResponsavelTarefa;


@Repository
@Transactional
public interface HistTransfResponslTarefaRepository extends JpaRepository<HistoricoTransferenciaResponsavelTarefa, Long>{

	@Query(value = "SELECT u FROM HistoricoTransferenciaResponsavelTarefa u where u.id_historico = ?1")
	List<HistoricoTransferenciaResponsavelTarefa> findByHistoricoIdHistorico(Long idHistorico);	
	
	
	@Query(value = "SELECT u FROM HistoricoTransferenciaResponsavelTarefa u where u.id_tarefa = ?1 order by dt_criacao")
	List<HistoricoTransferenciaResponsavelTarefa> findByHistoricoIdTarefa(Long idTarefa);	
	
	@Query(value = "SELECT u FROM HistoricoTransferenciaResponsavelTarefa u where u.id_tarefa = ?1 order by dt_criacao")
	List<HistoricoTransferenciaResponsavelTarefa> findByHistoricoIdMudanca( Long IdMudanca );	
	
}
