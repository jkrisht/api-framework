package com.enums;

public enum PetStatus {

    AVAILABLE("available"), PENDING("pending"), SOLD("sold"), INVALID("invalid");

    private final String status;

    PetStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
