package com.example.parceltracking.repository;

import com.example.parceltracking.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, String> {

    @Query("SELECT p FROM Parcel p WHERE p.guest.id = :guestId AND p.pickedUp = false")
    List<Parcel> findUnclaimedParcelsByGuestId(@Param("guestId") String guestId);

    @Query("SELECT p FROM Parcel p WHERE p.guest.id = :guestId")
    List<Parcel> findByGuestId(@Param("guestId") String guestId);
}
