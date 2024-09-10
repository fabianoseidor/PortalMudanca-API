package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ListaAprovadores;
import br.com.portalmudanca.model.dto.ListaAprovadoresDTO;

@Repository
@Transactional
public interface ListaAprovadoresRepository extends JpaRepository<ListaAprovadores, Long>{
	
	
	@Query(value = "select new br.com.portalmudanca.model.dto.ListaAprovadoresDTO( "
				 + "               mud.id_mudanca                                  "
				 + "             , mud.dt_criacao                                  "
				 + "             , mud.login_user                                  "
				 + "             , mud.statusMudanca                               "
				 + "             , mud.titulo_mudanca                              "
				 + "             , cri.criticidade                                 "
				 + "             , imp.impacto_mudanca                             "
				 + "             , tim.tipo_mudanca                                "
				 + "             , lia.id_lista_aprovadores                        "
				 + "             , lia.dt_criacao as dt_criacao_aprovacao          "
				 + "             , lia.dt_aprovacao                                "
				 + "             , lia.statusAprovacao                             "
				 + "             , apr.aprovador                                   "
				 + "             , apr.login_aprovador                             "
				 + "         )                                                     "
				 + "        from                                                   "
				 + "             ListaAprovadores   lia                            "
				 + "        JOIN lia.aprovadores    apr                            "
				 + "        JOIN lia.mudanca        mud                            "
				 + "        JOIN mud.criticidade    cri                            "
				 + "        JOIN mud.impactoMudanca imp                            "
				 + "        JOIN mud.tipoMudanca    tim                            "
				 + " WHERE lia.mudanca.statusMudanca = 'AGUARDANDO_APROVACOES'     " )
	List<ListaAprovadoresDTO> buscarListaAprovacao();

	/* ******************************************************************************************* */
	/*                                                                                             */
	/*          Realiza a Aprovacao e Reprovacao das GMUD, atualizando os Campos:                  */
	/*                                                                                             */
	/* ******************************************************************************************* */
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE lista_aprovadores SET  dt_aprovacao = getdate() , status_aprovacao = 1  WHERE id_lista_aprovadores = ?1")
	void updateAprovacaoGmud(Long id);
	
	@Query(value = "SELECT QTT_N.QTT_N_APROVADO - QTY_A.QTY_APROVADO  FROM  ( "
			     + "  SELECT COUNT( status_aprovacao ) QTY_APROVADO FROM lista_aprovadores WHERE id_mudanca = ?1 AND status_aprovacao = 1 )AS QTY_A,"
			     + "( SELECT COUNT( status_aprovacao ) QTT_N_APROVADO FROM lista_aprovadores WHERE id_mudanca = ?1 ) AS QTT_N", nativeQuery = true)
	Long getQtyAprovacao(Long idMudanca);	
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE lista_aprovadores SET  dt_reprovacao = getdate() WHERE id_lista_aprovadores = ?1")
	void updateReprovacaoGmud(Long id);
	
	
	
	
	
	
	
	
	
	
	
	
}
