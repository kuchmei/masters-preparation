package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.dto.FinanceDto;
import com.example.poc.masterspreparation.dto.ScheduleRequestDto;
import com.example.poc.masterspreparation.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/date")
    public String FinancesByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date-for-finance";
    }

    @GetMapping(value = "/byDate")
    public String getFinanceForByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto,
                                      @ModelAttribute FinanceDto financeDto,
                                      Model model,
                                      Principal principal) {
        model.addAttribute("financesMinus", attendanceService.getFinanceMinus(
                attendanceService.getAttendanceScheduleForDate(LocalDateTime.parse(attendanceRequestDto.getStartDate()),
                        LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName())));
        model.addAttribute("financesPlus", attendanceService.getFinancePlus(
                attendanceService.getAttendanceScheduleForDate(LocalDateTime.parse(attendanceRequestDto.getStartDate()),
                        LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName())));

        return "finances";
    }
}
