package com.example.poc.masterspreparation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalReviewDto {

    private String name;
    private Integer goal;
    private String comment;
}
