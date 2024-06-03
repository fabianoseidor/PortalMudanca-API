package br.com.portalmudanca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.portalmudanca.model.Users;
import br.com.portalmudanca.repository.UsersRepository;

@Service
public class ImplementacaoUserDetailService implements UserDetailsService{

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users users = usersRepository.findUsersByLogin(username);
		
		if(users == null) {
			throw new UsernameNotFoundException("Usario nao foi encontrado");
		}
		
		return new User(users.getUsername(), users.getPassword(), users.getAuthorities());
	}

}
