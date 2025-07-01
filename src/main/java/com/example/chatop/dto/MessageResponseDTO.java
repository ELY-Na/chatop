package com.example.chatop.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MessageResponseDTO {

    private int id;

    private String message;

    private LocalDateTime created_at;

    private int rental_id;

    private int user_id;

}
