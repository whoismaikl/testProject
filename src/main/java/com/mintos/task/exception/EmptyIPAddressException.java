package com.mintos.task.exception;

public class EmptyIPAddressException extends RuntimeException{

    public EmptyIPAddressException(String message) {
        super(message);
    }

}