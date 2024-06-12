package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.Mudanca;

@Repository
@Transactional
public interface MudancaRepository extends JpaRepository<Mudanca, Long>{

	@Query(value = "SELECT * FROM mudanca where id_mudanca = ?1", nativeQuery = true)
	Mudanca findByIdMudanca(Long id_mudanca);
	
	@Query(value = "SELECT * FROM mudanca where upper(trim(titulo_mudanca)) like %?1%", nativeQuery = true)
	List<Mudanca> buscarMudancaPor(String titulo_mudanca);
	
	@Query(value = "SELECT * FROM mudanca where upper(trim(status_mudanca)) like %?1%", nativeQuery = true)
	List<Mudanca> buscarMudancaPorStatus(String status_mudanca);
	
	@Query(value = "select s from Mudanca s where s.titulo_mudanca like %?1%")
	List<Mudanca> listaMudancaPorTitulo(String titulo);
		
}
