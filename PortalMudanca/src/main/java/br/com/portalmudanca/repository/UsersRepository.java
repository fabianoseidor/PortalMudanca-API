package br.com.portalmudanca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.portalmudanca.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

	 @Query(value = "SELECT u.* FROM users u where u.login = ?1", nativeQuery = true)
	 Users findUsersByLogin(String login);
	 
}
