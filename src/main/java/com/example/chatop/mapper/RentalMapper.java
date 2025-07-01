package com.example.chatop.mapper;

import org.springframework.stereotype.Component;

import com.example.chatop.dto.RentalResponseDTO;
import com.example.chatop.dto.UserSummaryDTO;
import com.example.chatop.model.Rental;
import com.example.chatop.model.User;

@Component
public class RentalMapper {

    public RentalResponseDTO toDto(Rental rental) {
        RentalResponseDTO dto = new RentalResponseDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setDescription(rental.getDescription());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());

        User owner = rental.getOwner();

        if (owner != null) {

            UserSummaryDTO ownerDto = new UserSummaryDTO();
            ownerDto.setId(owner.getId());
            ownerDto.setName(owner.getName());
            ownerDto.setEmail(owner.getEmail());
            dto.setOwner_id(owner.getId());
        }

        return dto;
    }

}