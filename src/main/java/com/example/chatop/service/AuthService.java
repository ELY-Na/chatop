package com.example.chatop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.chatop.model.User;

@Service
public class AuthService {
	public void checkOwnership(UserDetails userDetails, User targetUser) {
		if (!userDetails.getUsername().equalsIgnoreCase(targetUser.getEmail())) {
			throw new SecurityException("You are not authorized to access this resource");
		}
	}
}
