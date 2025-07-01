package com.example.chatop.controller;

import com.example.chatop.dto.AuthRequest;
import com.example.chatop.dto.AuthResponse;
import com.example.chatop.dto.RegisterRequest;
import com.example.chatop.model.Rental;
import com.example.chatop.model.Message;
import com.example.chatop.model.User;
import com.example.chatop.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private com.example.chatop.security.JwtUtils jwtUtils;

	// 🔐 Enregistre un nouvel utilisateur
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("Email already exists");
		}

		User newUser = User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();

		userRepository.save(newUser);
		String token = jwtUtils.generateToken(newUser.getEmail());

		return ResponseEntity.ok(new AuthResponse(token));
	}

	// 🔓 Authentifie un utilisateur et renvoie un JWT
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		Authentication authentication = authenticationManager.authenticate( // utilisé pour authentifier et générer un
				// token.
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String token = jwtUtils.generateToken(request.getEmail());

		// Récupération de l'utilisateur connecté
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Récupération de ses données
		List<Rental> rentals = user.getRentals();
		List<Message> messages = user.getMessages();

		return ResponseEntity.ok(new AuthResponse(token));
	}

	@GetMapping("/me")
	// utilisé après le login pour accéder à l'utilisateur via le token dans une
	// route comme /me.
	public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
		String email = userDetails.getUsername();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not foud"));

		return ResponseEntity.ok(user);
	}

}
