package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.CategoriaPadrao;
import br.com.portalmudanca.model.ItensCategoriaPadrao;

@Repository
@Transactional
public interface ItensCategoriaPadraoRepository extends JpaRepository<ItensCategoriaPadrao, Long>{

	@Query(value = "select s from ItensCategoriaPadrao s where categoriaPadrao = ?1")
    List<ItensCategoriaPadrao> findCategoriaPadrao(CategoriaPadrao categoriaPadrao);
	
	@Query(value = "select s from ItensCategoriaPadrao s where id_itens_cat_padrao = ?1")
	ItensCategoriaPadrao findByIdItensCatPadrao(Long id_itens_cat_padrao);
	
	@Query(value = "select a.* from itens_categoria_padrao a where upper(trim(titulo_cat_padrao)) = ?1", nativeQuery = true)
	ItensCategoriaPadrao findByTituloCatPadrao(String titulo_cat_padrao);
	
	@Query(value = "SELECT * FROM itens_categoria_padrao where id_categoria_padrao = ?1 order by prioridade", nativeQuery = true)
	List<ItensCategoriaPadrao> findByItemCategoriaPadrao(Long idCategoriaPadrao);
}
