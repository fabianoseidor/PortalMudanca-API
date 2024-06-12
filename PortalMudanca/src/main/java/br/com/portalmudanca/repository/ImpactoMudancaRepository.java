package br.com.portalmudanca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ImpactoMudanca;
import java.util.List;


@Repository
@Transactional
public interface ImpactoMudancaRepository extends JpaRepository<ImpactoMudanca, Long>{


	@Query(value = "select a.* from impacto_mudanca a where a.id_impacto_mudanca = ?1", nativeQuery = true)
	ImpactoMudanca findById_impacto_mudanca(Long id_impacto_mudanca);
	
	@Query(value = "select a.* from impacto_mudanca a where upper(trim(a.impacto_mudanca)) like %?1%", nativeQuery = true)
	List<ImpactoMudanca> findByImpactoMudancas(String impacto_mudanca);
	
	@Query(value = "select a.* from impacto_mudanca a where upper(trim(a.impacto_mudanca)) = ?1", nativeQuery = true)
	ImpactoMudanca findByImpactoMudanca(String impacto_mudanca);
	
}
