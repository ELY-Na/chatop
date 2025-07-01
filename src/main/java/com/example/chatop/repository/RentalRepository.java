package com.example.chatop.repository;

import com.example.chatop.model.Rental;
import com.example.chatop.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

	@Query("SELECT r FROM Rental r JOIN FETCH r.owner")
	List<Rental> findByOwner(User ownerId);

}