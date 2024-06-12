package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ExecutorCategoriaPadrao;

@Repository
@Transactional
public interface ExecutorCategoriaPadraoRepository extends JpaRepository<ExecutorCategoriaPadrao, Long>{

	@Query(value = "SELECT * FROM executor_categoria_padrao where id_exec_categ_padrao = ?1", nativeQuery = true)
	ExecutorCategoriaPadrao findById_exec_categ_padrao(Long id_exec_categ_padrao);
	
	@Query(value = "SELECT * FROM executor_categoria_padrao where id_categoria_padrao = ?1", nativeQuery = true)
	List<ExecutorCategoriaPadrao> buscaByCategoriaPorCategoria(Long id_categoria_padrao);
	
}
