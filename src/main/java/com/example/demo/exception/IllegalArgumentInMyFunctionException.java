package com.example.demo.exception;

public class IllegalArgumentInMyFunctionException extends RuntimeException{
    public IllegalArgumentInMyFunctionException() {
    }

    public IllegalArgumentInMyFunctionException(String message) {
        super(message);
    }
}
