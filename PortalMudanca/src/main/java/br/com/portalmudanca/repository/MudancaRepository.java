package br.com.portalmudanca.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.enums.StatusRdm;
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.dto.ListaMudancaNaoFechadasDTO;

@Repository
@Transactional
public interface MudancaRepository extends JpaRepository<Mudanca, Long>{

	@Query(value = "SELECT * FROM mudanca where id_mudanca = ?1", nativeQuery = true)
	Mudanca findByIdMudanca(Long id_mudanca);
	
	@Query(value = "SELECT * FROM mudanca where upper(trim(titulo_mudanca)) like %?1%", nativeQuery = true)
	List<Mudanca> buscarMudancaPor(String titulo_mudanca);
	
	@Query(value = "select mu                            "
			     + " from                                "
			     + "      Mudanca                    mu  "
			     + " JOIN mu.mudancaClientesAfetados mca "
			     + " JOIN mca.clientesAfetados       cla "
			     + " where cla.id_clientes_af = ?1       " )
	List<Mudanca> listaMudancaClienteAfetados(Long idClientesAf);
	
	@Query(value = "SELECT u FROM Mudanca u where u.statusMudanca = ?1")
	List<Mudanca> buscarMudancaPorStatus( StatusRdm status_mudanca);
	
	@Query(value = "select s from Mudanca s where s.titulo_mudanca like %?1%")
	List<Mudanca> listaMudancaPorTitulo(String titulo);

//	@Query(value = "SELECT TOP (10) * FROM mudanca WHERE status_mudanca IN ('ABERTA', 'APROVADA', 'AGUARDANDO_EXECUCAO', 'EM_EXECUCAO', 'AGUARDANDO_APROVACOES') order by dt_criacao DESC", nativeQuery = true)
	@Query(value = "SELECT  * FROM mudanca WHERE status_mudanca IN ('ABERTA', 'APROVADA', 'AGUARDANDO_EXECUCAO', 'EM_EXECUCAO', 'AGUARDANDO_APROVACOES') order by id_mudanca OFFSET ?1 ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
	List<Mudanca> listaMudancaAbertas( int offsetBegin, int offsetEnd );

	@Query(value = "SELECT COUNT(mud.id_mudanca) FROM mudanca as mud WHERE status_mudanca IN ('ABERTA', 'APROVADA', 'AGUARDANDO_EXECUCAO', 'EM_EXECUCAO', 'AGUARDANDO_APROVACOES') ", nativeQuery = true)
	Long qtyTotalMudancaAbertas( );
		
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
	
	@Transactional(readOnly = true)
	@Query( value = " select new br.com.portalmudanca.model.dto.ListaMudancaNaoFechadasDTO(   "
			                         + "   mud.id_mudanca "
			                         + " , mud.dt_criacao "
			                         + " , mud.dt_alteracao "
			                         + " , mud.dt_fechamento "
			                         + " , mud.statusMudanca "
			                         + " , mud.titulo_mudanca "
			                         + " , mud.email_solicitante "
			                         + " , dmu.dt_inicio "
			                         + " , dmu.hr_inicio "
			                         + " , dmu.dt_final "
			                         + " , dmu.hr_final  ) "
			                         + " from Mudanca  mud "
			                         + "  JOIN mud.dadosMudanca dmu"
			                         + " where mud.dt_fechamento = null"
			                         + "   and ?1 > dmu.dt_final ")
	List<ListaMudancaNaoFechadasDTO> gettMudancasNaoFechadas(LocalDate dtAtual);

 	@Query(value = "SELECT HOST_NAME()", nativeQuery = true)
 	String getHostName( );                                                                       

	@Query(value = "SELECT id_descomissionamento FROM dados_mudanca where id_mudanca = ?1", nativeQuery = true)
	Long getIdDescomissionamentoPortal( Long id );
	

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE [PORTALMULTICLOUD_DEV].[dbo].[DESCOMISSIONAMENTO] SET "
			                         + "    DESLIGAMENTO_AMBIENTE    = 1 ,                           "
			                         + "    USER_DESLIGAMENTO        = ?2,                           "
			                         + "    DT_DESLIGAMENTO_AMBIENTE = CURRENT_TIMESTAMP             "
			                         + " WHERE ID_DESCOMISSIONAMENTO = ?1                            ")
	void upDesligueInfraDEV(Long id, String userDesligamento);		

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE [PORTALMULTICLOUD_PRD].[dbo].[DESCOMISSIONAMENTO] SET "
			                         + "    DESLIGAMENTO_AMBIENTE    = 1 ,                           "
			                         + "    USER_DESLIGAMENTO        = ?2 ,                          "
			                         + "    DT_DESLIGAMENTO_AMBIENTE = CURRENT_TIMESTAMP             "
			                         + " WHERE ID_DESCOMISSIONAMENTO = ?1                            ")
	void upDesligueInfraPRD(Long id, String userDesligamento);		

	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE mudanca SET status_mudanca = 'AGUARDANDO_APROVACOES', dt_alteracao = NULL WHERE id_mudanca = ?1")
	void updateResetAprovacao(Long id);		
	
}
