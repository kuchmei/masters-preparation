package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AttendanceService {

    void saveAttendance(AttendanceScheduleDto attendanceScheduleDto);

    List<AttendanceScheduleDto> getAttendanceScheduleForDate(LocalDateTime startDate, LocalDateTime finishDate, String email);

    List<AttendanceScheduleDto> getAttendanceScheduleForToday(String email);

    Double getFinance(List<AttendanceScheduleDto> attendanceScheduleDtos);
}
