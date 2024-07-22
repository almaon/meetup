package com.example.meetup.authorization;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsManager {

	private final UserRepository repository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new SecurityUser(repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with username = " + username)));
	}

	@Override
	@Transactional
	public void createUser(UserDetails user) {
		var securityUser = (SecurityUser) user;
		repository.save(new User(		
				securityUser.getId(),
				securityUser.getUsername(), 
				securityUser.getPassword(),
				securityUser.getAuthorities().stream().map(a -> a.getAuthority()).toList()));
	}

	@Override
	@Transactional
	public void updateUser(UserDetails user) {
		repository.save(((SecurityUser) user).createUser());
	}

	@Override
	@Transactional
	public void deleteUser(String username) {
		User userDetails = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No User found for username -> " + username));
		repository.delete(userDetails);
	}


	@Override
	@Transactional
	public void changePassword(String oldPassword, String newPassword) {
	
	}

	@Override
	public boolean userExists(String username) {
		return repository.findByUsername(username).isPresent();
	}
}
