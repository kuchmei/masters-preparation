package com.example.poc.masterspreparation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StatisticDto {
    public StatisticDto(String name, Integer sum, Integer count, Integer middle) {
        this.name = name;
        this.sum = sum;
        this.count = count;
        this.middle = middle;
    }

    private String name;
    private Integer sum;
    private Integer count;
    private Integer middle;
    private Integer priority;

}
