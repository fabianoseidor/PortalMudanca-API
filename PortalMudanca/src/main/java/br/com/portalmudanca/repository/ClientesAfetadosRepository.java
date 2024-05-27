package br.com.portalmudanca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portalmudanca.model.ClientesAfetados;

@Repository
@Transactional
public interface ClientesAfetadosRepository extends JpaRepository<ClientesAfetados, Long>{

}
