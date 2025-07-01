package com.example.chatop.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.chatop.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.map(user -> new org.springframework.security.core.userdetails.User(
						user.getEmail(), // email utilisé comme username
						user.getPassword(), // mot de passe hashé
						Collections.emptyList() // pas de rôles
				))
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}
}
