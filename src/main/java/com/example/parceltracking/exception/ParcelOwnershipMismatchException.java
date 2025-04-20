package com.example.parceltracking.exception;

public class ParcelOwnershipMismatchException extends RuntimeException {
    public ParcelOwnershipMismatchException(String message) {
        super(message);
    }
}
