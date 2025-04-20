package com.example.parceltracking.repository;

import com.example.parceltracking.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, String> {
}