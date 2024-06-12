package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.Aprovadores;

@Repository
@Transactional
public interface AprovadoresRepository extends JpaRepository<Aprovadores, Long>{
	
	@Query(value = "SELECT id_aprovadores, aprovador, obs, dt_criacao FROM aprovadores where id_aprovadores = ?1", nativeQuery = true)
	Aprovadores findByIdAprovadores(Long idAprovadores);
	
	@Query(value = "SELECT id_aprovadores, aprovador, obs, dt_criacao  FROM aprovadores where upper(trim(aprovador)) like %?1%", nativeQuery = true)
	List<Aprovadores> buscarAprovadorNome(String aprovador);
	
	@Query(value = "SELECT id_aprovadores, aprovador, obs, dt_criacao  FROM aprovadores where upper(trim(aprovador)) = ?1", nativeQuery = true)
	Aprovadores existAprovador(String aprovador);
		

}
