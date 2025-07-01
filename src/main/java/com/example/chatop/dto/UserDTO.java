package com.example.chatop.dto;

import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserDTO {

    private int id;

    @JsonAlias("name") // accepte "name" depuis le frontend
    @NotBlank
    @JsonProperty("name") // envoie "name" dans les réponses JSON
    private String name;

    @NotBlank
    @NonNull
    private String email;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

}