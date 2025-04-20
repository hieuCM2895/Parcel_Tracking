package com.example.parceltracking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Guest {

    @Id
    @NotBlank(message = "Guest ID must not be blank")
    private String id;

    @NotBlank(message = "Full name of guest must not be blank")
    private String fullName;

    @NotNull(message = "Check-in time is required")
    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private boolean checkedIn;
}
