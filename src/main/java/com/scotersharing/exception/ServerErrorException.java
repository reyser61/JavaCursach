package com.scotersharing.exception;

public class ServerErrorException extends RuntimeException{
    public ServerErrorException() {
    }

    public ServerErrorException(String message) {
        super(message);
    }
}
