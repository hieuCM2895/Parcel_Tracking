package com.example.parceltracking.service;

import com.example.parceltracking.model.Parcel;

import java.util.List;

public interface ParcelService {
    Parcel receiveParcel(Parcel parcel);
    List<Parcel> getParcelsByGuest(String guestId);
    void markParcelAsPickedUp(String guestId, String parcelId);
}