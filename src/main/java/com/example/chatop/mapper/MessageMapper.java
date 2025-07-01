package com.example.chatop.mapper;

import org.mapstruct.Mapper;

import com.example.chatop.dto.MessageDTO;
import com.example.chatop.model.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDTO toDto(Message message);

    Message toEntity(MessageDTO dto);
}
