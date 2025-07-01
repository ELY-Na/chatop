package com.example.chatop.controller;

import com.example.chatop.dto.MessageResponseDTO;
import com.example.chatop.model.Message;
import com.example.chatop.model.Rental;
import com.example.chatop.model.User;
import com.example.chatop.repository.RentalRepository;
import com.example.chatop.repository.UserRepository;
import com.example.chatop.service.MessageService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Endpoints to manage messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Message> createMessage(
            @RequestBody MessageResponseDTO messageRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        String messageText = messageRequest.getMessage();
        Integer rentalId = messageRequest.getRental_id();
        Integer userId = messageRequest.getUser_id();

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setMessage(messageText);
        message.setRental(rental);
        message.setUser(user);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        Message savedMessage = messageService.createMessage(message, rental, user);
        return ResponseEntity.ok(savedMessage);
    }
}
