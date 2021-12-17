package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.dto.ScheduleRequestDto;
import com.example.poc.masterspreparation.dto.StatisticDto;
import com.example.poc.masterspreparation.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/statistic")
public class StatisticController {

    private final AttendanceService attendanceService;

    @GetMapping("/date")
    public String StatisticByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date-for-statistic";
    }

    @GetMapping(value = "/byDate")
    public String getStatisticByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto,
                                     @ModelAttribute StatisticDto statisticDto,
                                     Model model,
                                     Principal principal) {
        model.addAttribute("statisticDtos", attendanceService.getStatistic(
                attendanceService.getAttendanceScheduleForDate(LocalDateTime.parse(attendanceRequestDto.getStartDate()),
                        LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName())));

        return "statistics";
    }
}
