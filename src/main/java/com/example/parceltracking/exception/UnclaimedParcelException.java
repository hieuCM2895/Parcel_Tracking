package com.example.parceltracking.exception;

public class UnclaimedParcelException extends RuntimeException {
    public UnclaimedParcelException(String message) {
        super(message);
    }
}