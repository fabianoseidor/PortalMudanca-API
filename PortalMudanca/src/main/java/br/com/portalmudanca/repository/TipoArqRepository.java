package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.TipoArq;

@Repository
@Transactional
public interface TipoArqRepository extends JpaRepository<TipoArq, Long>{

	@Query(value = "select a.* from tipo_arq a where a.id_tipo_arq = ?1", nativeQuery = true)
	TipoArq findById_tipo_arq(Long id_tipo_arq);
	
	@Query(value = "select a.* from tipo_arq a where upper(trim(a.tipo_arq)) like %?1%", nativeQuery = true)
	List<TipoArq> findByTipoArqs(String tipo_arq);

	@Query(value = "select a.* from tipo_arq a where upper(trim(a.tipo_arq)) = ?1", nativeQuery = true)
	TipoArq findByTipoArq(String tipo_arq);

}
