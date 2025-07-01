package com.example.chatop.service;

import com.example.chatop.model.User;
import com.example.chatop.repository.UserRepository;
import com.example.chatop.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    // Cherche un utilisateur par son identifiant.
    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = jwtUtils.extractName(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
