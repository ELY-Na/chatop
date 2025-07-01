package com.example.chatop.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatop.model.Message;
import com.example.chatop.model.Rental;
import com.example.chatop.model.User;
import com.example.chatop.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message createMessage(Message message, Rental rental, User user) {
        message.setRental(rental); // lien vers Rental
        message.setUser(user); // lien vers User
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

}
