package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ListaGrupoCliente;

@Repository
@Transactional
public interface ListaGrupoClienteRepository extends JpaRepository<ListaGrupoCliente, Long>{

	
	@Query(value = " SELECT lgc "
			     + " FROM "
			     + "    ListaGrupoCliente  lgc "
			     + " JOIN lgc.grupoCliente gp  "
			     + " where gp.id_grupo_cliente = ?1")
	List<ListaGrupoCliente> ListaGrupoClientePorGrupo( Long id );

	@Query(value = " SELECT lgc "
		     + " FROM "
		     + "    ListaGrupoCliente      lgc "
		     + " JOIN lgc.clientesAfetados ca  "
		     + " where ca.id_clientes_af = ?1  ")
   List<ListaGrupoCliente> ListaGrupoClientePorCliente( Long id );

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "delete lista_grupo_cliente where id_grupo_cliente = ?1")
	void deleteListaGrupoCliente( Long id );

	@Query(value = " SELECT count(lgc.id_lista_grupo_cliente) "
		         + " FROM                                     "
		         + "    ListaGrupoCliente  lgc                "
		         + " JOIN lgc.grupoCliente gp                 "
		         + " JOIN lgc.clientesAfetados ca             "
		         + " where gp.id_grupo_cliente = ?1           "
		         + "   and ca.id_clientes_af   = ?2           ")
    Integer isExitsClienteGrupo( Long idGrupo, Long idCli);

}
