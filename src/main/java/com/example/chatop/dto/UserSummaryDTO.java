package com.example.chatop.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UserSummaryDTO {

    private int id;
    private String name;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private List<RentalDTO> rentals;

}
