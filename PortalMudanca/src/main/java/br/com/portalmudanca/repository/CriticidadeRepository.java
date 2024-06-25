package br.com.portalmudanca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.Criticidade;
import java.util.List;


@Repository
@Transactional
public interface CriticidadeRepository extends JpaRepository<Criticidade, Long>{

	@Query(value = "select a.* from criticidade a where a.id_criticidade = ?1", nativeQuery = true)
	Criticidade findById_criticidade(Long id_criticidade);
	
	@Query(value = "select a.* from criticidade a where upper(trim(a.criticidade)) like %?1%", nativeQuery = true)
	List<Criticidade> findByCriticidades(String criticidade);
	
	@Query(value = "select a.* from criticidade a where upper(trim(a.criticidade)) = ?1", nativeQuery = true)
	Criticidade findByCriticidade(String criticidade);

	@Query(value = "select a.* from criticidade a", nativeQuery = true)
	List<Criticidade> findByCriticidades();

}
