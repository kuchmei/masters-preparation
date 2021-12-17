package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.dto.*;
import com.example.poc.masterspreparation.service.AttendanceService;
import com.example.poc.masterspreparation.service.GoalServiceImpl;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping()
    public String listAttendance(Model model,
                                @ModelAttribute("price") TotalPriceDto totalPriceDto,
                                Principal principal) {
        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleWithClientsForToday(principal.getName());


        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));
        model.addAttribute("attendances", attendanceScheduleForToday);
        return "attendances";
    }

    @GetMapping("/date")
    public String listCustomersByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date-for-attendance";
    }


    @GetMapping("/new")
    public String createAttendanceForm(Model model) {
        AttendanceScheduleDto attendance = new AttendanceScheduleDto();
        model.addAttribute("attendance", attendance);
        return "create_attendance";
    }

    @GetMapping(value = "/byDate")
    public String getAttendanceByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto,
                                      @ModelAttribute("price") TotalPriceDto totalPriceDto, Model model, Principal principal) {
        List<AttendanceScheduleDto> attendanceScheduleForDate = attendanceService.getAttendanceScheduleWithClientsForDate
                (LocalDateTime.parse(attendanceRequestDto.getStartDate()), LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName());
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForDate));
        model.addAttribute("attendances", attendanceScheduleForDate);
        return "attendances";
    }

    @PostMapping(value = "")
    public String addAttendance(@ModelAttribute("attendance") AttendanceScheduleDto attendance,
                                @ModelAttribute("price") TotalPriceDto totalPriceDto,
                                Model model, Principal principal) {
        attendanceService.saveAttendance(attendance);

        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleWithClientsForToday(principal.getName());
        model.addAttribute("attendances", attendanceScheduleForToday);
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));
        return "attendances";
    }

    @GetMapping("/edit/{id}")
    public String editAttendanceForm(@PathVariable Long id, @NotNull Model model) {
        model.addAttribute("attendance", attendanceService.getAttendanceScheduleById(id));
        return "edit_attendance";
    }

    @PostMapping("/{id}")
    public String updateAttendance(@PathVariable Long id,
                                   @ModelAttribute("attendance") AttendanceScheduleDto attendanceScheduleDto,
                                   @ModelAttribute("price") TotalPriceDto totalPriceDto, Principal principal, Model model) {
        attendanceService.updateAttendance(attendanceScheduleDto, id);
        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleForToday(principal.getName());
        model.addAttribute("attendances", attendanceScheduleForToday);
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));

        return "attendances";
    }

    @GetMapping("/{id}")
    public String deleteAttendance(@PathVariable Long id, Model model,
                                   @ModelAttribute("price") TotalPriceDto totalPriceDto,
                                   Principal principal) {
        attendanceService.deleteAttendanceById(id);
        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleForToday(principal.getName());
        model.addAttribute("attendances", attendanceScheduleForToday);
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));
        return "attendances";
    }

}
