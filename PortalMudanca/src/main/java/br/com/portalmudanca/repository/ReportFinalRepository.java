package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.ReportFinal;

@Repository
@Transactional
public interface ReportFinalRepository extends JpaRepository<ReportFinal, Long>{

	@Query( value = "SELECT u FROM ReportFinal u where u.mudanca = ?1" )
	List<ReportFinal> findByMudanca(Mudanca mudanca);
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE report_final SET report_final = ?2 WHERE id_mudanca = ?1")
	void updateFechamentoReportFinal(Long id, String reportFinal);		
	
}
