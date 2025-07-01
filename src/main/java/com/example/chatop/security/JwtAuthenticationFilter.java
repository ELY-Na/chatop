package com.example.chatop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	public JwtUtils jwtUtils;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	// Filtre exécuté une seule fois par requête HTTP
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		// 1. On récupère le token dans l'en-tête Authorization
		String jwt = getJwtFromRequest(request);

		try {
			// 2. Si on a un token valide, on vérifie l'utilisateur
			if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
				String email = jwtUtils.extractName(jwt);
				// 3. On charge l'utilisateur depuis la base de données
				var userDetails = userDetailsService.loadUserByUsername(email);
				// 4. On crée un objet d'authentification et on l'enregistre dans le contexte;
				var authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			System.out.println("Erreur dans le filtre JWT : " + ex.getMessage());
		}

		// 5. On continue la chaîne de filtres
		filterChain.doFilter(request, response);
	}

	// Méthode pour extraire le token du header Authorization
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// Le token est dans l'en-tête "Authorization: Bearer <token>"
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // on enlève "Bearer "
		}
		return null;
	}
}
