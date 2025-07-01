package com.example.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @JsonProperty("name")
    @NotBlank
    private String name;

    private String email;

    private String password;
}
