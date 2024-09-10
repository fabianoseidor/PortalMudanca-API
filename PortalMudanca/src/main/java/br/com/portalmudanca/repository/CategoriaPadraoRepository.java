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
	
	@Query(value = "select a.* from categoria_padrao a", nativeQuery = true)
	List<CategoriaPadrao> listaCategoriaPadroesTodas();

	@Query(value = "select a.* from categoria_padrao a where ativar_contrato = 0 and desligar_contrato = 0", nativeQuery = true)
	List<CategoriaPadrao> listaCategoriaPadroesNormal();	
	
	@Query(value = "select a.* from categoria_padrao a where ativar_contrato = 1", nativeQuery = true)
	List<CategoriaPadrao> listaCategoriaPadroesAtivacao();

	@Query(value = "select a.* from categoria_padrao a where desligar_contrato = 1", nativeQuery = true)
	List<CategoriaPadrao> listaCategoriaPadroesDesativacao();

	@Query(value = "select u from CategoriaPadrao u where u.id_categoria_padrao = ?1")
    CategoriaPadrao listaByIdCategoriaPadroes( Long id );
		
	@Query(value = "SELECT cat.* FROM categoria_padrao AS cat ORDER BY id_categoria_padrao OFFSET ?1 ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
	List<CategoriaPadrao> listaCategoriaPadroesPaginacao(int offsetBegin, int offsetEnd);
	
	@Query(value = "SELECT COUNT( cat.id_categoria_padrao ) FROM categoria_padrao AS cat", nativeQuery = true)
	Long qtyCategoriaPadraoCadastrada( );
   
	@Query(value = "select COUNT( a.id_mudanca ) from mudanca as a where id_categoria_padrao = ?1", nativeQuery = true)
	Long chcekCategoriaPadraoMucanca( Long id );

 	@Query(value = "SELECT HOST_NAME()", nativeQuery = true)
 	String getHostName( );                                                                       

}
