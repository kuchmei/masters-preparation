package com.example.poc.masterspreparation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendanceScheduleDto {

    private Long id;

    private String date;

    private String comment;

    private String clientEmail;

    private String workerEmail;

    private Double sum;
}
