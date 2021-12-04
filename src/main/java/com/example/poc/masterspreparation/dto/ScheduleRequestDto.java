package com.example.poc.masterspreparation.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ScheduleRequestDto {

    private String startDate;
    private String finishDate;
}
