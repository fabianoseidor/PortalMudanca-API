package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ImpactoMudanca;
import br.com.portalmudanca.model.StatusAtividade;

@Repository
@Transactional
public interface StatusAtividadeRepository  extends JpaRepository<StatusAtividade, Long>{

	
	@Query(value = "select a.* from status_atividade a where a.id_status_atividade = ?1", nativeQuery = true)
	ImpactoMudanca findByIdStatusAtividade(Long id_status_atividade);
	
	@Query(value = "select a.* from status_atividade a where upper(trim(a.status_atividade)) like %?1%", nativeQuery = true)
	List<ImpactoMudanca> findByStatusAtividades(String status_atividade);
	
	@Query(value = "select a.* from status_atividade a where upper(trim(a.status_atividade)) = ?1", nativeQuery = true)
	ImpactoMudanca existStatusAtividade(String status_atividade);
	
	
}
