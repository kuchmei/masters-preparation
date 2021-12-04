package com.example.poc.masterspreparation.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Boolean isAdmin;
}
