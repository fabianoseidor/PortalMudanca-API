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
	private UsersRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users usuario = usuarioRepository.findUsersByLogin(username);/* Recebe o login pra consulta */

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}

		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
	}
}
