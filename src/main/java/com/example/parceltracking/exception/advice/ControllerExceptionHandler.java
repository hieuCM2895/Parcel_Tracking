package com.example.parceltracking.exception.advice;

import com.example.parceltracking.constants.ErrorMessages;
import com.example.parceltracking.dto.ErrorCodeResponse;
import com.example.parceltracking.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GuestNotFoundException.class)
    public ResponseEntity<ErrorCodeResponse> handleGuestNotFound(GuestNotFoundException ex) {
        log.error("Guest not found: {}", ex.getMessage());
        return build(HttpStatus.NOT_FOUND,
                ErrorMessages.GUEST_NOT_FOUND_CODE,
                ErrorMessages.GUEST_NOT_FOUND_MSG,
                ex.getMessage());
    }

    @ExceptionHandler(ParcelNotFoundException.class)
    public ResponseEntity<ErrorCodeResponse> handleParcelNotFound(ParcelNotFoundException ex) {
        log.error("Parcel not found: {}", ex.getMessage());
        return build(HttpStatus.NOT_FOUND,
                ErrorMessages.PARCEL_NOT_FOUND_CODE,
                ErrorMessages.PARCEL_NOT_FOUND_MSG,
                ex.getMessage());
    }

    @ExceptionHandler(UnclaimedParcelException.class)
    public ResponseEntity<ErrorCodeResponse> handleUnclaimed(UnclaimedParcelException ex) {
        log.warn("Unclaimed parcel: {}", ex.getMessage());
        return build(HttpStatus.BAD_REQUEST,
                ErrorMessages.UNCLAIMED_PARCEL_CODE,
                ErrorMessages.UNCLAIMED_PARCEL_MSG,
                ex.getMessage());
    }

    @ExceptionHandler(ParcelToCheckedOutGuestException.class)
    public ResponseEntity<ErrorCodeResponse> handleCheckedOutParcelError(ParcelToCheckedOutGuestException ex) {
        log.warn("📦 Tried to receive parcel for checked-out guest: {}", ex.getMessage());
        return build(
                HttpStatus.BAD_REQUEST,
                ErrorMessages.PARCEL_FOR_CHECKED_OUT_GUEST_CODE,
                ErrorMessages.PARCEL_FOR_CHECKED_OUT_GUEST_MSG,
                ex.getMessage()
        );
    }

    @ExceptionHandler(ParcelOwnershipMismatchException.class)
    public ResponseEntity<ErrorCodeResponse> handleParcelOwnershipMismatch(ParcelOwnershipMismatchException ex) {
        log.error("🔐 Parcel ownership mismatch: {}", ex.getMessage());
        return build(
                HttpStatus.BAD_REQUEST,
                ErrorMessages.PARCEL_OWNERSHIP_CODE,
                ErrorMessages.PARCEL_OWNERSHIP_MSG,
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCodeResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error: ", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorMessages.SYSTEM_ERROR_CODE,
                ErrorMessages.SYSTEM_ERROR_MSG,
                ex.getMessage());
    }
    private ResponseEntity<ErrorCodeResponse> build(HttpStatus status, String code, String message, String detail) {
        return new ResponseEntity<>(new ErrorCodeResponse(code, message, detail), status);
    }
}
