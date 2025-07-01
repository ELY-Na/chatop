package com.example.chatop.mapper;

import com.example.chatop.dto.UserDTO;
import com.example.chatop.dto.UserSummaryDTO;
import com.example.chatop.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO toDto(User user); // Pour les cas internes (avec password)

	User toEntity(UserDTO dto); // Pour convertir un DTO entrant en User

	UserSummaryDTO toSummaryDto(User user); // Pour les réponses API sans password
}