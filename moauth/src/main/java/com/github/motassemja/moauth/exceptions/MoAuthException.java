package com.github.motassemja.moauth.exceptions;

/**
 * Created by moja on 12.06.2017.
 */

public abstract class MoAuthException extends Exception {
    protected MoAuthException(String exceptionMsg) {
        super(exceptionMsg);
    }
    protected MoAuthException(String exceptionMsg, Exception e) {
        super(exceptionMsg, e);
    }
    public abstract MoAuthExceptionType getExceptionType();
}
