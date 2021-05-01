package com.bank.account.exception;

public class MissingRequestParamException extends RuntimeException {
    public MissingRequestParamException(String message) {
        super(message);
    }
}
