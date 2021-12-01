package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;

import java.util.Date;
import java.util.List;

public interface AttendanceService {

    void saveAttendance(AttendanceScheduleDto attendanceScheduleDto);

    List<AttendanceScheduleDto> getAttendanceScheduleForDate(Date startDate, Date finishDate, String email);

    List<AttendanceScheduleDto> getAttendanceScheduleForToday(String email);

    Double getFinance(List<AttendanceScheduleDto> attendanceScheduleDtos);
}
