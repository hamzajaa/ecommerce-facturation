package com.ecommerce.facturation.exception;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String message){
        super(message);
    }
    public BankAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
