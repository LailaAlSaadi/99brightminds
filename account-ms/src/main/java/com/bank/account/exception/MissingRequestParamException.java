package com.bank.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRequestParamException extends RuntimeException {
    public MissingRequestParamException(String message) {
        super(message);
    }
}
