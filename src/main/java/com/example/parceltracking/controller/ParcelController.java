package com.example.parceltracking.controller;

import com.example.parceltracking.model.Parcel;
import com.example.parceltracking.service.ParcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping
    public Parcel receiveParcel(@Valid @RequestBody Parcel parcel) {
        return parcelService.receiveParcel(parcel);
    }

    @GetMapping("/guest/{guestId}")
    public List<Parcel> getParcelsByGuest(@PathVariable String guestId) {
        return parcelService.getParcelsByGuest(guestId);
    }

    @PostMapping("/guest/{guestId}/pickup/{parcelId}")
    public void pickupParcel(@PathVariable String guestId, @PathVariable String parcelId) {
        parcelService.markParcelAsPickedUp(guestId, parcelId);
    }
}
