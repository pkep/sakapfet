package com.kp2s.sakapfet.common.client.exception;

public class DuplicateResourceException extends SAFException{

    private String message;

    private int code;

    public DuplicateResourceException(String message, int code) {
        super(message, code);
    }
}
