package com.example.chatop.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "messages")
@Data // Add getters/setters/toString/equals/hashCode
@NoArgsConstructor // Generate an empty constructor for entities
@AllArgsConstructor // Generate a constructor with all fields
@Builder
@ToString(exclude = "rental")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key
    @Column(name = "message_id")
    private int id;

    @NotBlank(message = "message is mandatory")
    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

}
