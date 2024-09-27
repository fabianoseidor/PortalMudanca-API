package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.GrupoCliente;


@Repository
@Transactional
public interface GrupoClienteRepository  extends JpaRepository<GrupoCliente, Long>{

	@Query(value = "SELECT u FROM GrupoCliente u where u.id_grupo_cliente = ?1")
	GrupoCliente findByIdGrupoCliente(Long idGrupoCliente);
	
	@Query(value = "SELECT u FROM GrupoCliente u")
	List<GrupoCliente> ListaGrupoCliente( );

}
