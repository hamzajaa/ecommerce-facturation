package com.ecommerce.facturation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExceptionMessage {
    private String exceptionMessage;
    private LocalDate date;
    private HttpStatus httpStatus;


}
