package com.example.chatop.service;

import com.example.chatop.dto.RentalDTO;
import com.example.chatop.dto.RentalResponseDTO;
import com.example.chatop.mapper.RentalMapper;
import com.example.chatop.model.Rental;
import com.example.chatop.model.User;
import com.example.chatop.repository.RentalRepository;
import com.example.chatop.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    private Path uploadPath = Paths.get("uploads");

    @Autowired
    private RentalMapper rentalMapper;

    public List<RentalResponseDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElse(null);
    }

    // Enregistre l'image dans uploads
    private String savePicture(MultipartFile file) throws IOException {

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Path filePath = uploadPath.resolve(fileName);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName; // Stocké dans picture
    }

    @Override
    public Rental createRental(RentalDTO dto, String userEmail, String filename) throws IOException {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rental rental = Rental.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .surface(dto.getSurface())
                .price(dto.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .owner(owner)
                .build();

        // Si nouvelle image est fournie, sauvegarder et remplacer
        if (dto.getPicture() != null && !dto.getPicture().isEmpty()) {
            String pictureUrl = savePicture(dto.getPicture());
            rental.setPicture(pictureUrl);
        }

        return rentalRepository.save(rental);
    }

    // Update qui prend RentalDTO pour multipart + validation propriétaire
    @Override
    public Rental updateRental(Integer id, RentalDTO dto, String userEmail) throws IOException {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // Vérification que l'utilisateur connecté est bien le propriétaire
        if (!rental.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Not Authorized");
        }

        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(rental.getPicture()); // Garde l'ancienne image par défaut
        rental.setDescription(dto.getDescription());

        // Si nouvelle image est fournie, sauvegarder et remplacer
        if (dto.getPicture() != null && !dto.getPicture().isEmpty()) {
            String pictureUrl = savePicture(dto.getPicture());
            rental.setPicture(pictureUrl);
        }

        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }

}
