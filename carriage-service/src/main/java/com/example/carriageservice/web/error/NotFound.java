package com.example.carriageservice.web.error;

public class NotFound extends RuntimeException {

    public NotFound(String reason) {
        super(reason);
    }
}
