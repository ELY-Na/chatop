package com.example.chatop.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RentalResponseDTO {
	private int id;
	private String name;
	private String description;
	private int surface;
	private int price;
	private String picture; // URL de l'image

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private int owner_id;// ID du propriétaire, si nécessaire pour le frontend

}
