package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.CategoriaPadrao;

@Repository
@Transactional
public interface CategoriaPadraoRepository extends JpaRepository<CategoriaPadrao, Long>{
	
	@Query(value = "select a.* from categoria_padrao a where a.id_categoria_padrao = ?1", nativeQuery = true)
	CategoriaPadrao findById_categoria_padrao(Long id_categoria_padrao);

	@Query(value = "select a.* from categoria_padrao a where upper(trim(a.categoria_padrao)) like %?1%", nativeQuery = true)
	List<CategoriaPadrao> findByCategoriaPadroes(String categoria_padrao);
	
	@Query(value = "select a.* from categoria_padrao a where upper(trim(a.categoria_padrao)) = ?1", nativeQuery = true)
	CategoriaPadrao findByCategoriaPadrao(String categoria_padrao);
	
}
