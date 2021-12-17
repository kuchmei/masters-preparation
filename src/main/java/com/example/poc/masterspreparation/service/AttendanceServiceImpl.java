package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;
import com.example.poc.masterspreparation.dto.FinanceDto;
import com.example.poc.masterspreparation.dto.StatisticDto;
import com.example.poc.masterspreparation.dto.TotalPriceDto;
import com.example.poc.masterspreparation.model.AttendanceSchedule;
import com.example.poc.masterspreparation.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        attendanceSchedule.setWorker(customerService.findByEmail(attendanceScheduleDto.getWorkerEmail()));
        attendanceSchedule.setSum(attendanceScheduleDto.getSum());
        attendanceRepository.save(attendanceSchedule);
    }

    public void updateAttendance(AttendanceScheduleDto attendanceScheduleDto, Long id) {
        AttendanceSchedule attendanceSchedule = new AttendanceSchedule();
        attendanceSchedule.setId(id);
        attendanceSchedule.setComment(attendanceScheduleDto.getComment());
        attendanceSchedule.setDate(LocalDateTime.parse(attendanceScheduleDto.getDate()));
        attendanceSchedule.setClient(customerService.findByEmail(attendanceScheduleDto.getClientEmail()));
        attendanceSchedule.setWorker(customerService.findByEmail(attendanceScheduleDto.getWorkerEmail()));
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

    @Override
    public List<AttendanceScheduleDto> getAttendanceScheduleWithClientsForDate(LocalDateTime startDate, LocalDateTime finishDate, String email) {
        return attendanceRepository.findAll().stream()
                .filter(attendanceSchedule -> attendanceSchedule.getWorker().getEmail().equalsIgnoreCase(email) ||
                        attendanceSchedule.getClient().getEmail().equalsIgnoreCase(email))
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

    @Override
    public List<AttendanceScheduleDto> getAttendanceScheduleWithClientsForToday(String email) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String time = dtf.format(LocalDateTime.now());
        return attendanceRepository.findAll().stream()
                .filter(attendanceSchedule -> attendanceSchedule.getWorker().getEmail().equalsIgnoreCase(email) ||
                        attendanceSchedule.getClient().getEmail().equalsIgnoreCase(email))
                .filter(attendanceSchedule -> attendanceSchedule.getDate().toString().contains(time))
                .map(this::toAttendanceScheduleDto)
                .collect(Collectors.toList());
    }

    public Set<FinanceDto> getFinanceMinus(List<AttendanceScheduleDto> attendanceScheduleDtos) {
        Set<FinanceDto> financeDtos = new HashSet<>();
        Set<String> comments = attendanceScheduleDtos.stream()
                .map(AttendanceScheduleDto::getComment)
                .collect(Collectors.toSet());

        for (String comment : comments) {
            Integer sum = attendanceScheduleDtos.stream()
                    .filter(attendanceScheduleDto -> attendanceScheduleDto.getComment().equals(comment))
                    .mapToInt(AttendanceScheduleDto::getSum)
                    .filter(number -> number < 0)
                    .sum();
            if (sum < 0) {
                financeDtos.add(new FinanceDto(comment, sum));

            }
        }


        return financeDtos;
    }

    public Set<FinanceDto> getFinancePlus(List<AttendanceScheduleDto> attendanceScheduleDtos) {
        Set<FinanceDto> financeDtos = new HashSet<>();
        Set<String> comments = attendanceScheduleDtos.stream()
                .map(AttendanceScheduleDto::getComment)
                .collect(Collectors.toSet());

        for (String comment : comments) {
            Integer sum = attendanceScheduleDtos.stream()
                    .filter(attendanceScheduleDto -> attendanceScheduleDto.getComment().equals(comment))
                    .mapToInt(AttendanceScheduleDto::getSum)
                    .filter(number -> number > 0)
                    .sum();
            if (sum > 0) {
                financeDtos.add(new FinanceDto(comment, sum));

            }
        }

        return financeDtos;
    }

    public AttendanceScheduleDto getAttendanceScheduleById(Long id) {
        return toAttendanceScheduleDto(attendanceRepository.findById(id).get());
    }


    public void deleteAttendanceById(Long id) {
        AttendanceSchedule attendanceSchedule = attendanceRepository.getOne(id);
        attendanceRepository.delete(attendanceSchedule);
    }

    public List<StatisticDto> getStatistic(List<AttendanceScheduleDto> attendanceScheduleDtos) {
        List<StatisticDto> statisticDtos = new ArrayList<>();
        List<Integer> sums = new ArrayList<>();
        Set<String> emails = attendanceScheduleDtos.stream()
                .map(AttendanceScheduleDto::getClientEmail)
                .collect(Collectors.toSet());
        for (String email : emails) {
            Integer sum = attendanceScheduleDtos.stream()
                    .filter(attendanceScheduleDto -> attendanceScheduleDto.getClientEmail().equals(email))
                    .mapToInt(AttendanceScheduleDto::getSum)
                    .filter(number -> number > 0)
                    .sum();
            Integer count = attendanceScheduleDtos.stream()
                    .filter(attendanceScheduleDto -> attendanceScheduleDto.getClientEmail().equals(email))
                    .collect(Collectors.toList()).size();
            if (sum > 0) {
                statisticDtos.add(new StatisticDto(email, sum, count, sum / count));
                sums.add(sum);
            }
        }
        Collections.sort(sums);
        Collections.reverse(sums);
        for (Integer sum : sums) {
            Optional<StatisticDto> statisticDto = statisticDtos.stream()
                    .filter(dto -> dto.getSum().equals(sum) && dto.getPriority() == null)
                    .findFirst();

            statisticDto.get().setPriority(sums.indexOf(sum) + 1);
        }
        statisticDtos.sort(Comparator.comparing(StatisticDto::getPriority));
        return statisticDtos;
    }

    public TotalPriceDto getTotalPrice(List<AttendanceScheduleDto> attendanceScheduleDtos) {

        Integer price = attendanceScheduleDtos.stream()
                .mapToInt(AttendanceScheduleDto::getSum)
                .sum();

        TotalPriceDto totalPriceDto = new TotalPriceDto();
        totalPriceDto.setTotalPrice(price);
        return totalPriceDto;
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
