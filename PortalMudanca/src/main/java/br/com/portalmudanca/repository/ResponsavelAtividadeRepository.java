package br.com.portalmudanca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ResponsavelAtividade;

@Repository
@Transactional
public interface ResponsavelAtividadeRepository extends JpaRepository<ResponsavelAtividade, Long>{

	@Query(value = "select a.* from responsavel_atividade a where a.id_responsavel_atividade = ?1", nativeQuery = true)
	ResponsavelAtividade findById_responsavel_atividade(Long id_responsavel_atividade);
	
	@Query(value = "select a.* from responsavel_atividade a where upper(trim(a.responsavel_atividade)) like %?1%", nativeQuery = true)
	List<ResponsavelAtividade> findByResponsavelAtividades(String responsavel_atividade);
	
	@Query(value = "select a.* from responsavel_atividade a where upper(trim(a.responsavel_atividade)) = ?1", nativeQuery = true)
    ResponsavelAtividade findByResponsavelAtividade(String responsavel_atividade);

	@Query(value = "select a.* from responsavel_atividade a order by a.responsavel_atividade", nativeQuery = true)
	List<ResponsavelAtividade> listaResponsavelAtividades();

}
