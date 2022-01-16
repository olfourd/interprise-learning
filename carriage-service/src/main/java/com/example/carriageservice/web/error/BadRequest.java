package com.example.carriageservice.web.error;

public class BadRequest extends RuntimeException {

    public BadRequest(String reason) {
        super(reason);
    }
}
