package com.example.demo.exception;

public class NotFoundRecordInDataException extends RuntimeException{
    public NotFoundRecordInDataException() {
    }

    public NotFoundRecordInDataException(String message) {
        super(message);
    }
}
