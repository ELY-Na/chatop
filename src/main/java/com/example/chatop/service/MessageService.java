package com.example.chatop.service;

import com.example.chatop.model.Message;
import com.example.chatop.model.Rental;
import com.example.chatop.model.User;

public interface MessageService {

    Message createMessage(Message Message, Rental rental, User user);

}
