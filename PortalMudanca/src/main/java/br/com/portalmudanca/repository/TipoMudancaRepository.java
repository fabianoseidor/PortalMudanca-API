package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.TipoMudanca;

@Repository
@Transactional
public interface TipoMudancaRepository extends JpaRepository<TipoMudanca, Long>{

	@Query(value = "select a.* from tipo_mudanca a where a.id_tipo_mudanca = ?1", nativeQuery = true)
	TipoMudanca findById_tipo_mudanca(Long id_tipo_mudanca);
	
	@Query(value = "select a.* from tipo_mudanca a where upper(trim(a.tipo_mudanca)) like %?1%", nativeQuery = true)
	List<TipoMudanca> findByTipoMudancas(String tipo_mudanca);
	
	@Query(value = "select a.* from tipo_mudanca a where upper(trim(a.tipo_mudanca)) = ?1", nativeQuery = true)
    TipoMudanca findByTipo_mudanca(String tipo_mudanca);

	
}
