package com.example.chatop.service;

import com.example.chatop.dto.RentalDTO;
import com.example.chatop.dto.RentalResponseDTO;
import com.example.chatop.model.Rental;

import java.io.IOException;
import java.util.List;
// import java.util.Optional;

public interface RentalService {

    List<RentalResponseDTO> getAllRentals();

    Rental createRental(RentalDTO rentalDTO, String userEmail, String filename) throws IOException;

    Rental updateRental(Integer id, RentalDTO dto, String userEmail) throws IOException;

    Rental getRentalById(Integer id);

}
