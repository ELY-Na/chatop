package com.example.chatop.controller;

import com.example.chatop.dto.RentalDTO;
import com.example.chatop.dto.RentalResponseDTO;
import com.example.chatop.mapper.RentalMapper;
import com.example.chatop.model.Rental;
import com.example.chatop.service.AuthService;
import com.example.chatop.service.FileStorageService;
import com.example.chatop.service.RentalService;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private final RentalMapper rentalMapper;

    public RentalController(RentalService rentalService, RentalMapper rentalMapper) {
        this.rentalService = rentalService;
        this.rentalMapper = rentalMapper;
    }

    @GetMapping
    public Map<String, List<RentalResponseDTO>> getAllRentals() {
        List<RentalResponseDTO> rentals = rentalService.getAllRentals();
        Map<String, List<RentalResponseDTO>> map = new HashMap<>();
        map.put("rentals", rentals);
        return map;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Integer id) {
        Rental rental = rentalService.getRentalById(id);
        if (rental == null) {
            return ResponseEntity.notFound().build();
        }
        RentalResponseDTO dto = rentalMapper.toDto(rental); // 👈 tu dois appeler le mapper qui remplit owner_id
        return ResponseEntity.ok(dto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Rental> createRental(
            @ModelAttribute @Valid RentalDTO rentalDTO,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException, Exception {

        // 1. Enregistre le fichier traitement ici avec rentalDTO.getPicture()
        String filename = fileStorageService.saveFile(rentalDTO.getPicture());

        Rental rental = rentalService.createRental(rentalDTO,
                userDetails.getUsername(), filename);
        // return ResponseEntity.ok().build();
        return ResponseEntity.ok(rental);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Rental> updateRental(
            @PathVariable Integer id,
            @ModelAttribute @Valid RentalDTO rentalDTO,
            @AuthenticationPrincipal UserDetails userDetails) throws Exception {

        Rental existingRental = rentalService.getRentalById(id);

        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }

        authService.checkOwnership(userDetails, existingRental.getOwner());

        Rental updatedRental = rentalService.updateRental(id, rentalDTO, userDetails.getUsername());
        return ResponseEntity.ok(updatedRental);
    }
}
