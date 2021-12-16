package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;
import com.example.poc.masterspreparation.dto.FinanceDto;
import com.example.poc.masterspreparation.dto.StatisticDto;
import com.example.poc.masterspreparation.dto.TotalPriceDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AttendanceService {

    void saveAttendance(AttendanceScheduleDto attendanceScheduleDto);

    void updateAttendance(AttendanceScheduleDto attendanceScheduleDto, Long id);

    List<AttendanceScheduleDto> getAttendanceScheduleForDate(LocalDateTime startDate, LocalDateTime finishDate, String email);

    List<AttendanceScheduleDto> getAttendanceScheduleWithClientsForDate(LocalDateTime startDate, LocalDateTime finishDate, String email);

    List<AttendanceScheduleDto> getAttendanceScheduleForToday(String email);

    List<AttendanceScheduleDto> getAttendanceScheduleWithClientsForToday(String email);

    Set<FinanceDto> getFinanceMinus(List<AttendanceScheduleDto> attendanceScheduleDtos);

    Set<FinanceDto> getFinancePlus(List<AttendanceScheduleDto> attendanceScheduleDtos);

    AttendanceScheduleDto getAttendanceScheduleById (Long id);

    void deleteAttendanceById(Long id);

    TotalPriceDto getTotalPrice(List<AttendanceScheduleDto> attendanceScheduleDtos);

    List<StatisticDto> getStatistic(List<AttendanceScheduleDto> attendanceScheduleDtos);
}
