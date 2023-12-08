package com.ecommerce.facturation.exception;

public class BankAccountBalanceNotFoundException extends Exception {
    public BankAccountBalanceNotFoundException(String message){
        super(message);
    }
    public BankAccountBalanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
