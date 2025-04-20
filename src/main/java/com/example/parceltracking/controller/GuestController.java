package com.example.parceltracking.controller;

import com.example.parceltracking.model.Guest;
import com.example.parceltracking.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/checkin")
    public Guest checkIn(@Valid @RequestBody Guest guest) {
        return guestService.checkIn(guest);
    }

    @PostMapping("/{guestId}/checkout")
    public void checkOut(@PathVariable String guestId) {
        guestService.checkOut(guestId);
    }

    @GetMapping("/{guestId}/status")
    public boolean isGuestCheckedIn(@PathVariable String guestId) {
        return guestService.isGuestCheckedIn(guestId);
    }
}
