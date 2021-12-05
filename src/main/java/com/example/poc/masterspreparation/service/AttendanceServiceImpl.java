package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;
import com.example.poc.masterspreparation.model.AttendanceSchedule;
import com.example.poc.masterspreparation.model.Customer;
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
        AttendanceSchedule attendanceSchedule = new AttendanceSchedule();
        attendanceSchedule.setComment(attendanceScheduleDto.getComment());
        attendanceSchedule.setDate(LocalDateTime.parse(attendanceScheduleDto.getDate()));
        attendanceSchedule.setClient(customerService.findByEmail(attendanceScheduleDto.getClientEmail()));
        attendanceSchedule.setWorker( customerService.findByEmail(attendanceScheduleDto.getWorkerEmail()));
        attendanceSchedule.setSum(attendanceScheduleDto.getSum());
        attendanceRepository.save(attendanceSchedule);
    }
    public void updateAttendance(AttendanceScheduleDto attendanceScheduleDto, Long id) {
        AttendanceSchedule attendanceSchedule = new AttendanceSchedule();
        attendanceSchedule.setId(id);
        attendanceSchedule.setComment(attendanceScheduleDto.getComment());
        attendanceSchedule.setDate(LocalDateTime.parse(attendanceScheduleDto.getDate()));
        attendanceSchedule.setClient(customerService.findByEmail(attendanceScheduleDto.getClientEmail()));
        attendanceSchedule.setWorker( customerService.findByEmail(attendanceScheduleDto.getWorkerEmail()));
        attendanceSchedule.setSum(attendanceScheduleDto.getSum());
        attendanceRepository.save(attendanceSchedule);
    }

    public List<AttendanceScheduleDto> getAttendanceScheduleForDate(LocalDateTime startDate, LocalDateTime finishDate, String email) {

        return attendanceRepository.findAll().stream()
                .filter(attendanceSchedule -> attendanceSchedule.getWorker().getEmail().equalsIgnoreCase(email))
                .filter(attendanceSchedule -> attendanceSchedule.getDate().compareTo(startDate) >= 0 && attendanceSchedule.getDate().compareTo(finishDate) <= 0)
                .map(this::toAttendanceScheduleDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceScheduleDto> getAttendanceScheduleForToday(String email) {
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
        for (Double s : sumList) {
            allSum += s;
        }
        return allSum;
    }

    public AttendanceScheduleDto getAttendanceScheduleById(Long id) {
        return toAttendanceScheduleDto(attendanceRepository.findById(id).get());
    }

    @Override
    public void deleteAttendanceById(Long id) {
        AttendanceSchedule attendanceSchedule = attendanceRepository.getOne(id);
        attendanceRepository.delete(attendanceSchedule);
    }

    private AttendanceScheduleDto toAttendanceScheduleDto(AttendanceSchedule attendanceSchedule) {
        AttendanceScheduleDto attendanceScheduleDto = new AttendanceScheduleDto();
        attendanceScheduleDto.setId(attendanceSchedule.getId());
        attendanceScheduleDto.setDate(attendanceSchedule.getDate().toString());
        attendanceScheduleDto.setComment(attendanceSchedule.getComment());
        attendanceScheduleDto.setClientEmail(attendanceSchedule.getClient().getEmail());
        attendanceScheduleDto.setWorkerEmail(attendanceSchedule.getWorker().getEmail());
        attendanceScheduleDto.setSum(attendanceSchedule.getSum());

        return attendanceScheduleDto;
    }
}
