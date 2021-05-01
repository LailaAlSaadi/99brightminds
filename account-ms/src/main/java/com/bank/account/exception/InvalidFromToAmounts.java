package com.bank.account.exception;

public class InvalidFromToAmounts extends RuntimeException {
    public InvalidFromToAmounts(String message) {
        super(message);
    }
}
