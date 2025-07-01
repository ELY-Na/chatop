package com.example.chatop.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "users")
@Data // Add getters/setters/toString/equals/hashCode
@NoArgsConstructor // Generate an empty constructor for entities
@AllArgsConstructor // Generate a constructor with all fields
@Builder // Build object easily
@ToString(exclude = { "rentals", "messages" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key
    @Column(name = "user_id")
    private int id;

    @NotBlank(message = "name is mandatory")
    // @NonNull
    // @Column(nullable = false, unique = true) // validation
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "password is mandatory")
    @Column(nullable = false) // validation
    @JsonIgnore
    private String password;

    @Email(message = "Email invalid")
    @Column(nullable = false, unique = true) // validation
    @NotBlank(message = "email is mandatory")
    private String email;

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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Rental> rentals = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Message> messages;

}