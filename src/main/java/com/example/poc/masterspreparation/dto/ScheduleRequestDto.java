package com.example.poc.masterspreparation.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleRequestDto {

    private Date starDate;
    private Date finishDate;
}
