package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.Aprovadores;

@Repository
@Transactional
public interface AprovadoresRepository extends JpaRepository<Aprovadores, Long>{
	
	@Query(value = "SELECT a.* FROM aprovadores a where id_aprovadores = ?1", nativeQuery = true)
	Aprovadores findByIdAprovadores(Long idAprovadores);
	
	@Query(value = "SELECT a.* FROM aprovadores a where upper(trim(aprovador)) like %?1%", nativeQuery = true)
	List<Aprovadores> buscarAprovadorNome(String aprovador);
	
	@Query(value = "SELECT a.* FROM aprovadores a where upper(trim(aprovador)) = ?1", nativeQuery = true)
	Aprovadores existAprovador(String aprovador);
		
	@Query(value = "SELECT a.* FROM aprovadores a order by a.aprovador", nativeQuery = true)
	List<Aprovadores> buscarAprovador();
	
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE lista_aprovadores SET  dt_aprovacao = getdate() , status_aprovacao = 1  WHERE id_lista_aprovadores = ?1")
	void updateAprovacaoGmud(Long id);
	
	@Query(value = "SELECT QTT_N.QTT_N_APROVADO - QTY_A.QTY_APROVADO  FROM  ( "
			     + "  SELECT COUNT( status_aprovacao ) QTY_APROVADO FROM lista_aprovadores WHERE id_mudanca = ?1 AND status_aprovacao = 1 )AS QTY_A,"
			     + "( SELECT COUNT( status_aprovacao ) QTT_N_APROVADO FROM lista_aprovadores WHERE id_mudanca = ?1 ) AS QTT_N", nativeQuery = true)
	Long getQtyAprovacao(Long idMudanca);		

}
