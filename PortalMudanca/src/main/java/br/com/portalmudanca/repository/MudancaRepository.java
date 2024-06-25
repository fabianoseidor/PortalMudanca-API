package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.enums.StatusRdm;
import br.com.portalmudanca.model.Mudanca;

@Repository
@Transactional
public interface MudancaRepository extends JpaRepository<Mudanca, Long>{

	@Query(value = "SELECT * FROM mudanca where id_mudanca = ?1", nativeQuery = true)
	Mudanca findByIdMudanca(Long id_mudanca);
	
	@Query(value = "SELECT * FROM mudanca where upper(trim(titulo_mudanca)) like %?1%", nativeQuery = true)
	List<Mudanca> buscarMudancaPor(String titulo_mudanca);
	
	@Query(value = "SELECT u FROM Mudanca u where u.statusMudanca = ?1")
	List<Mudanca> buscarMudancaPorStatus( StatusRdm status_mudanca);
	
	@Query(value = "select s from Mudanca s where s.titulo_mudanca like %?1%")
	List<Mudanca> listaMudancaPorTitulo(String titulo);

	@Query(value = "SELECT TOP (10) * FROM mudanca WHERE status_mudanca IN ('ABERTA', 'APROVADA', 'AGUARDANDO_EXECUCAO', 'EM_EXECUCAO', 'AGUARDANDO_APROVACOES') order by dt_criacao DESC", nativeQuery = true)
	List<Mudanca> listaMudancaAbertas();
				
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE mudanca SET [status_mudanca] = 'AGUARDANDO_EXECUCAO', dt_alteracao = getdate() WHERE id_mudanca = ?1")
	void updateStatusGmudAguardadndoExec(Long id);		

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE mudanca SET [status_mudanca] = 'REJEITADA', dt_alteracao = getdate() WHERE id_mudanca = ?1")
	void updateStatusGmudRejeitada(Long id);		
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE mudanca SET status_mudanca = ?2, dt_alteracao = getdate() WHERE id_mudanca = ?1")
	void updateStatusGmud(Long id, StatusRdm status);		

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE mudanca SET status_mudanca = ?2, dt_alteracao = getdate(), dt_fechamento = getdate() WHERE id_mudanca = ?1")
	void updateFechamentoGmud(Long id, String status);		

	@Query(value = "SELECT u FROM Mudanca u where id_mudanca = ?1")
	Mudanca getMudancaPorId( Long id );
	
	
}
