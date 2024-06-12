package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ArquivosMudanca;

@Repository
@Transactional
public interface ArquivosMudancaRepository extends JpaRepository<ArquivosMudanca, Long>{

	@Query(value = "SELECT * FROM arquivos_mudanca where id_arquivos_mudanca = ?1", nativeQuery = true)
	ArquivosMudanca findByIdArquivosMudanca(Long id_arquivos_mudanca);
	
	@Query(value = "SELECT * FROM arquivos_mudanca where id_mudanca = ?1", nativeQuery = true)
	List<ArquivosMudanca> buscarArquivosMudancaPorIdMudanca(Long id_mudanca);

}
