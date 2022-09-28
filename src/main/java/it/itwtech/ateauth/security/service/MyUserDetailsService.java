package it.itwtech.ateauth.security.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.details.MyUserDetails;
import it.itwtech.ateauth.model.Role;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.repositories.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " not found");
		}

		Set<Role> roles = new HashSet<Role>();
		roles.add(user.getRole());
		return new MyUserDetails(user, roles);
	}
}
