package com.example.parceltracking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Parcel {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    private LocalDateTime receivedAt;

    private boolean pickedUp;

    private LocalDateTime pickedUpTime;
}
