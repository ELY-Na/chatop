package com.example.chatop.security;

import java.util.Date;
import java.security.Key;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private final Key jwtSecretKey; // Clé signée dérivée du secret pour signer les tokens
	private final int jwtExpirationMs; // Durée de validité du token en millisecondes

	// Injecte le secret et la durée d'expiration via le constructeur
	public JwtUtils(
			@Value("${app.secret.key}") String jwtSecret,
			@Value("${app.expiration-time}") int jwtExpirationMs) {
		this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes()); // Crée la clé signée
		this.jwtExpirationMs = jwtExpirationMs;
	}

	// Génère un token JWT pour l'utilisateur (email en "subject")
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username) // Sujet = email ou username
				.setIssuedAt(new Date()) // Date d’émission
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Date d’expiration
				.signWith(jwtSecretKey) // Signature avec clé secrète
				.compact(); // Construction finale du token
	}

	// Extrait le "subject" du token (username/email)
	public String extractName(String token) {
		return getClaims(token).getSubject();
	}

	// Parse le token et retourne les claims s’il est valide
	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(jwtSecretKey) // Clé pour vérification
				.build()
				.parseClaimsJws(token) // Analyse le JWT
				.getBody(); // Retourne les données (claims)
	}

	// Vérifie si le token est valide (signature + non expiré)
	public boolean validateToken(String token) {
		try {
			getClaims(token); // Lève une exception si invalide
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
