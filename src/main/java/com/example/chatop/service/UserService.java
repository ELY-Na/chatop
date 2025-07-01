package com.example.chatop.service;

import com.example.chatop.model.User;

import java.util.Optional;

public interface UserService { // créer une interface pour utliser des signatures de methodes sans corps

    Optional<User> getUserById(Integer id);

}
