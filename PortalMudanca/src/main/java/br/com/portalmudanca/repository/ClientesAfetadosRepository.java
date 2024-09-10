package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ClientesAfetados;

@Repository
@Transactional
public interface ClientesAfetadosRepository extends JpaRepository<ClientesAfetados, Long>{

	@Query(value = "SELECT * FROM clientes_afetados order by nome_cliente", nativeQuery = true)
	List<ClientesAfetados> buscarClientesAfetados();
	
	@Query(value = "SELECT a.* FROM clientes_afetados a where a.id_clientes_af = ?1", nativeQuery = true)
	ClientesAfetados findByIdClientesAfetados(Long id_clientes_af);
	
	@Query(value = "SELECT a.* FROM clientes_afetados a where id_cliente_portal = ?1", nativeQuery = true)
	ClientesAfetados findByIdClientesAfetadosPortal(Long id_clientes_af);
	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = " INSERT INTO [PORTALRDM_PRD].[dbo].[clientes_afetados] ( id_clientes_af, id_cliente_portal, dt_criacao, nome_cliente ) "
		   	                         + " SELECT                                                                                                                "
			                         + "      NEXT VALUE FOR [dbo].[seq_clientes_afetados]                                                                     "
			                         + "    , ID_CLIENTE                                                                                                       "
			                         + "    , getdate()                                                                                                        "
			                         + "    ,RAZAO_SOCIAL                                                                                                      "
			                         + "  FROM [PORTALMULTICLOUD_PRD].[dbo].[CLIENTE] AS CLI                                                                   "
			                         + " WHERE NOT EXISTS ( SELECT * FROM clientes_afetados PS WHERE PS.id_cliente_portal = CLI.ID_CLIENTE )                   ")
	void upNovosClientes( );		

	@Query(value = "select distinct cl.ciclo_update from clientes_afetados as cl", nativeQuery = true)
	List<String> getCicloUpdateCliente( );
	
	@Query(value = "SELECT id_clientes_af ,id_cliente_portal ,dt_criacao ,nome_cliente ,ciclo_update FROM clientes_afetados where ciclo_update = ?1 order by nome_cliente", nativeQuery = true)
	List<ClientesAfetados> getClientesAfetadosPorCiclo(String cicloUp);	
	
	@Query(value = "SELECT c FROM ClientesAfetados c where upper(c.nome_cliente) like %?1% order by c.nome_cliente")
	List<ClientesAfetados> listClientesAfetados(String cliente);	

	
	
}
