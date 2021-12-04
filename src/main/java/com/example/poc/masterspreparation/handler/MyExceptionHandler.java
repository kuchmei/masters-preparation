package com.example.poc.masterspreparation.handler;

import com.example.poc.masterspreparation.exception.CustomerCreationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerCreationException.class)
    public String handleException(){
        return "error";
    }
}
