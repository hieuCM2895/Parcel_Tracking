package com.example.parceltracking.service;

import com.example.parceltracking.model.Guest;

public interface GuestService {
    Guest checkIn(Guest guest);
    void checkOut(String guestId);
    boolean isGuestCheckedIn(String guestId);
}