package com.kp2s.sakapfet.common.client.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
abstract class SAFException extends Exception{

    protected String message;

    protected int code;

    public SAFException(String message, Throwable cause, String message1, int code) {
        super(message, cause);
        this.message = message1;
        this.code = code;
    }
}
