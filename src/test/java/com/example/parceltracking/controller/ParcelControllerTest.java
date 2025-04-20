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
public class ParcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Guest guest;

    @BeforeEach
    public void setup() {
        parcelRepository.deleteAll();
        guestRepository.deleteAll();
        guest = new Guest();
        guest.setId("guest-parcel");
        guest.setFullName("Parcel Tester");
        guest.setCheckInTime(LocalDateTime.now());
        guest.setCheckedIn(true);
        guestRepository.save(guest);
    }

    @Test
    public void testReceiveParcelSuccess() throws Exception {
        Parcel parcel = new Parcel();
        parcel.setId("parcel-01");
        parcel.setGuest(guest);

        mockMvc.perform(post("/parcels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("parcel-01"))
                .andExpect(jsonPath("$.guest.id").value("guest-parcel"))
                .andExpect(jsonPath("$.pickedUp").value(false));
    }

    @Test
    public void testReceiveParcelForUnknownGuest_shouldFail() throws Exception {
        Guest ghost = new Guest();
        ghost.setId("ghost-guest");

        Parcel parcel = new Parcel();
        parcel.setId("ghost-parcel");
        parcel.setGuest(ghost);

        mockMvc.perform(post("/parcels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcel)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("guest-404"));
    }

    @Test
    public void testListParcelsByGuest() throws Exception {
        Parcel parcel = new Parcel();
        parcel.setId("parcel-02");
        parcel.setGuest(guest);
        parcel.setReceivedAt(LocalDateTime.now());
        parcel.setPickedUp(false);
        parcelRepository.save(parcel);

        mockMvc.perform(get("/parcels/guest/guest-parcel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("parcel-02"));
    }

    @Test
    public void testMarkParcelAsPickedUp() throws Exception {
        Parcel parcel = new Parcel();
        parcel.setId("parcel-03");
        parcel.setGuest(guest);
        parcel.setPickedUp(false);
        parcel.setReceivedAt(LocalDateTime.now());
        parcelRepository.save(parcel);

        mockMvc.perform(post("/parcels/guest/guest-parcel/pickup/parcel-03"))
                .andExpect(status().isOk());

        Parcel updated = parcelRepository.findById("parcel-03").orElseThrow();
        assert updated.isPickedUp();
    }

    @Test
    public void testMarkParcelAsPickedUp_shouldParcelNotFound() throws Exception {
        Parcel parcel = new Parcel();
        parcel.setId("parcel-03");
        parcel.setGuest(guest);
        parcel.setPickedUp(false);
        parcel.setReceivedAt(LocalDateTime.now());
        parcelRepository.save(parcel);

        mockMvc.perform(post("/parcels/guest/guest-parcel/pickup/parcel-04"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("parcel-404"));
    }

    @Test
    public void testMarkParcelAsPickedUp_shouldParcelMismatch() throws Exception {
        Guest guest2 = new Guest();
        guest2.setId("guest-2");
        guest2.setFullName("Guest Two");
        guest2.setCheckedIn(true);
        guestRepository.save(guest2);

        Parcel parcel = new Parcel();
        parcel.setId("parcel-1");
        parcel.setGuest(guest);
        parcel.setPickedUp(false);
        parcelRepository.save(parcel);

        mockMvc.perform(post("/parcels/guest/guest-2/pickup/parcel-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("parcel-4003"))
                .andExpect(jsonPath("$.message").value("Parcel does not belong to the specified guest"))
                .andExpect(jsonPath("$.detail").value("Parcel with ID parcel-1 does not belong to guest ID guest-2"));
    }

}
