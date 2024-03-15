package com.kp2s.sakapfet.common.client.exception;

public class ResourceNotFoundException extends SAFException{

    private String message;

    private int code;

    public ResourceNotFoundException(String message, int code) {
        super(message, code);
    }

}
