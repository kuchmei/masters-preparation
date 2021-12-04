package com.example.poc.masterspreparation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class AttendanceScheduleDto {

    private String date;

    private String comment;

    private String clientEmail;

    private String workerEmail;

    private Double sum;
}
