package com.example.chatop.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "rentals")
@Data // Add getters/setters/toString/equals/hashCode
@NoArgsConstructor // Generate an empty constructor for entities
@AllArgsConstructor // Generate a constructor with all fields
@Builder
@ToString(exclude = { "owner" })
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key
    @Column(name = "rental_id")
    private int id;

    @NotBlank(message = "rental name is mandatory")
    @Column(nullable = false) // validation
    private String name;

    @Column(nullable = false) // validation
    private String description;

    @Column(nullable = false) // validation
    private int surface;

    @Column(nullable = false) // validation
    private int price;

    @Column(nullable = false) // validation
    private String picture;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // @Column(name = "ownerId", nullable = false)
    // private int ownerId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "rental_id")
    private List<Message> messages;

    // Lien avec le propriétaire
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore // évite la boucle infinie lors de la sérialisation
    private User owner;

}
