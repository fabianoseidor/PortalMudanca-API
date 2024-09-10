package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.AcaoPosAtividade;
import br.com.portalmudanca.model.Mudanca;

@Repository
@Transactional
public interface AcaoPosAtividadeRepository extends JpaRepository<AcaoPosAtividade, Long>{

	@Query(value = "SELECT u FROM AcaoPosAtividade u where u.mudanca = ?1")
	List<AcaoPosAtividade> findByMudanca(Mudanca mudanca);
	
	@Query(value = "SELECT u FROM AcaoPosAtividade u where u.id_acao_pos_atividade = ?1")
	List<AcaoPosAtividade> findById_acao_pos_atividade(Long id_acao_pos_atividade);

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE acao_pos_atividade SET plano_rollback = ?2 ,plano_teste = ?3 WHERE id_mudanca = = ?1")
	void updateAcaoPosAtividade( Long id, String planoRollback, String planoTeste );		

}
