package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;
import com.example.poc.masterspreparation.model.AttendanceSchedule;
import com.example.poc.masterspreparation.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final CustomerService customerService;


    public void saveAttendance(AttendanceScheduleDto attendanceScheduleDto) {
        AttendanceSchedule attendanceSchedule = AttendanceSchedule.builder()
                .comment(attendanceScheduleDto.getComment())
                .date(attendanceScheduleDto.getDate())
                .client(customerService.findByEmail(attendanceScheduleDto.getClientEmail()))
                .worker(customerService.findByEmail(attendanceScheduleDto.getWorkerEmail()))
                .sum(attendanceScheduleDto.getSum())
                .build();
        attendanceRepository.save(attendanceSchedule);
    }

    public List<AttendanceScheduleDto> getAttendanceScheduleForDate(Date startDate, Date finishDate, String email) {
        return attendanceRepository.findAll().stream()
                .filter(attendanceSchedule -> attendanceSchedule.getWorker().getEmail().equalsIgnoreCase(email))
                .filter(attendanceSchedule -> attendanceSchedule.getDate().compareTo(startDate) >= 0 && attendanceSchedule.getDate().compareTo(finishDate) <= 0)
                .map(this::toAttendanceScheduleDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceScheduleDto> getAttendanceScheduleForToday(String email){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String time = dtf.format(LocalDateTime.now());
        return attendanceRepository.findAll().stream()
                .filter(attendanceSchedule -> attendanceSchedule.getDate().toString().contains(time))
                .filter(attendanceSchedule -> attendanceSchedule.getWorker().getEmail().equalsIgnoreCase(email))
                .map(this::toAttendanceScheduleDto)
                .collect(Collectors.toList());
    }

    public Double getFinance(List<AttendanceScheduleDto> attendanceScheduleDtos) {
        Double allSum = (double) 0;
        List<Double> sumList = attendanceScheduleDtos.stream()
                .map(AttendanceScheduleDto::getSum)
                .collect(Collectors.toList());
        for ( Double s : sumList) {
            allSum += s;
        }
        return allSum;
    }

    private AttendanceScheduleDto toAttendanceScheduleDto(AttendanceSchedule attendanceSchedule) {
        return AttendanceScheduleDto.builder()
                .date(attendanceSchedule.getDate())
                .comment(attendanceSchedule.getComment())
                .clientEmail(attendanceSchedule.getClient().getEmail())
                .workerEmail(attendanceSchedule.getWorker().getEmail())
                .sum(attendanceSchedule.getSum())
                .build();
    }

}
