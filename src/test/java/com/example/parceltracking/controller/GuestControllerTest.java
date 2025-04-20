package com.example.parceltracking.controller;

import com.example.parceltracking.model.Guest;
import com.example.parceltracking.model.Parcel;
import com.example.parceltracking.repository.GuestRepository;
import com.example.parceltracking.repository.ParcelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        parcelRepository.deleteAll();
        guestRepository.deleteAll();
        Guest guest = new Guest();
        guest.setId("1");
        guest.setFullName("John Doe");
        guest.setCheckInTime(LocalDateTime.now());
        guest.setCheckedIn(true);
        guestRepository.save(guest);
    }

    @Test
    public void testCheckInAndStatus() throws Exception {
        Guest guest = new Guest();
        guest.setId("guest-track-1");
        guest.setFullName("Tracking Guest");
        guest.setCheckInTime(LocalDateTime.now());

        mockMvc.perform(post("/guests/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/guests/guest-track-1/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testReceiveParcelForCheckedOutGuest_shouldFail() throws Exception {
        Guest guest = new Guest();
        guest.setId("guest-checkout");
        guest.setFullName("Checked Out Guest");
        guest.setCheckInTime(LocalDateTime.now());
        guest.setCheckedIn(false);
        guest.setCheckOutTime(LocalDateTime.now());
        guestRepository.save(guest);

        Parcel parcel = new Parcel();
        parcel.setId("parcel-checkout");
        parcel.setGuest(guest);

        mockMvc.perform(post("/parcels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("parcel-4001"));
    }

    @Test
    public void testCheckedOutGuest_shouldSuccess() throws Exception {
        Guest guest = new Guest();
        guest.setId("guest-checkout-success");
        guest.setFullName("Checked Out Guest");
        guest.setCheckInTime(LocalDateTime.now());
        guest.setCheckedIn(true);
        guest.setCheckOutTime(LocalDateTime.now());
        guestRepository.save(guest);

        mockMvc.perform(post("/guests/guest-checkout-success/checkout"))
                .andExpect(status().isOk());

        Guest updated = guestRepository.findById("guest-checkout-success").orElseThrow();
        assert !updated.isCheckedIn();
        assert updated.getCheckOutTime() != null;
    }

    @Test
    public void testGuestForgetsParcel_shouldDetectUnclaimed() throws Exception {
        Guest guest = new Guest();
        guest.setId("guest-forget");
        guest.setFullName("Forgetful Guest");
        guest.setCheckInTime(LocalDateTime.now());
        guest.setCheckedIn(true);
        guestRepository.save(guest);

        Parcel parcel = new Parcel();
        parcel.setId("parcel-unclaimed");
        parcel.setGuest(guest);
        parcel.setPickedUp(false);
        parcel.setReceivedAt(LocalDateTime.now());
        parcelRepository.save(parcel);

        mockMvc.perform(post("/guests/guest-forget/checkout"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("parcel-4002"))
                .andExpect(jsonPath("$.message").value("Guest has unclaimed parcels"));
    }

}
