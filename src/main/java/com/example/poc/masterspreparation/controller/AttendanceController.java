package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.dto.ScheduleRequestDto;
import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;
import com.example.poc.masterspreparation.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping(value = "/attendance/today")
    public List<AttendanceScheduleDto> getAttendanceForToday(Principal principal) {
        return attendanceService.getAttendanceScheduleForToday(principal.getName());
    }

    @GetMapping(value = "/attendance/byDate")
    public List<AttendanceScheduleDto> getAttendanceByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto, Principal principal) {
        return attendanceService.getAttendanceScheduleForDate(attendanceRequestDto.getStarDate(), attendanceRequestDto.getFinishDate(), principal.getName());
    }

    @PostMapping(value = "/attendance/new")
    public void addAttendance(@ModelAttribute AttendanceScheduleDto attendanceScheduleDto) {
        attendanceService.saveAttendance(attendanceScheduleDto);
    }

    @GetMapping(value = "/finance/today")
    public Double getFinanceForToday(Principal principal) {
        return attendanceService.getFinance(attendanceService.getAttendanceScheduleForToday(principal.getName()));
    }

    @GetMapping(value = "/finance/byDate")
    public Double getFinanceForByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto, Principal principal) {
        return attendanceService.getFinance(
                attendanceService.getAttendanceScheduleForDate(attendanceRequestDto.getStarDate(),
                        attendanceRequestDto.getFinishDate(), principal.getName()));
    }


}
