package com.example.poc.masterspreparation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {

    private  Long id;
    private String revenueName;

    private Integer totalPrice;
}
