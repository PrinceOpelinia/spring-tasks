package com.prinsdev.tasks.domain.dto;

public class ErrorResponse {
    int status;
    String message;
    String details;

    public ErrorResponse(int value, String message, String description) {
    }
}
