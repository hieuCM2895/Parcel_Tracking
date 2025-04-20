package com.example.parceltracking.dto;

import lombok.Getter;

@Getter
public class ErrorCodeResponse {
    private String error;
    private String message;
    private String detail;

    public ErrorCodeResponse(String code, String message, String detail) {
        this.error = code;
        this.message = message;
        this.detail = detail;
    }
}
