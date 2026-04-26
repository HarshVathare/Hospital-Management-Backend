package com.withHarsh.MediCore.Error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class Apierror {
    private LocalDateTime timestamp;
    private String error;
    private HttpStatus statusCode;

    public Apierror(){
        this.timestamp = LocalDateTime.now();
    }

    public Apierror( String error, HttpStatus statusCode) {
        this();
        this.error = error;
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}