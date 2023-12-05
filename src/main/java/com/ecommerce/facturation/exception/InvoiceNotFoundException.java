package com.ecommerce.facturation.exception;

public class InvoiceNotFoundException extends RuntimeException {

        public InvoiceNotFoundException(String message) {
            super(message);
        }

        public InvoiceNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

}