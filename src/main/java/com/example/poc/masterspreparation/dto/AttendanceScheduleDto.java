package com.example.poc.masterspreparation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AttendanceScheduleDto {

    private Date date;

    private String comment;

    private String clientEmail;

    private String workerEmail;

    private Double sum;
}
