package com.example.poc.masterspreparation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceController {

    @GetMapping("/")
    public String getLoggedOutMessage(){
        return "Logged successfully";
    }
}
