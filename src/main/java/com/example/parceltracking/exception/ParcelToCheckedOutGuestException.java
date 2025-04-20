package com.example.parceltracking.exception;

public class ParcelToCheckedOutGuestException extends RuntimeException {
    public ParcelToCheckedOutGuestException(String message) {
        super(message);
    }
}