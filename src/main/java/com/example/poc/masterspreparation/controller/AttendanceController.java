package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.model.AttendanceSchedule;
import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
//
//    @GetMapping
//    public List<AttendanceSchedule> getAllCustomer() {
//
//    }
}
