package com.bank.account.exception;

public class InvalidFromToDates extends RuntimeException {

    public InvalidFromToDates(String message){
        super(message);
    }
}
