package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ClientesAfetados;

@Repository
@Transactional
public interface ClientesAfetadosRepository extends JpaRepository<ClientesAfetados, Long>{

	@Query(value = "SELECT id_clientes_af, id_cliente_portal, dt_criacao, nome_cliente FROM clientes_afetados", nativeQuery = true)
	List<ClientesAfetados> buscarClientesAfetados();
	
	@Query(value = "SELECT a.* FROM clientes_afetados a where a.id_clientes_af = ?1", nativeQuery = true)
	ClientesAfetados findByIdClientesAfetados(Long id_clientes_af);

	
}
