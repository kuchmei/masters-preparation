package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;
import com.example.poc.masterspreparation.dto.ScheduleRequestDto;
import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.service.AttendanceService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/attendance")
    public String listCustomers(Model model, Principal principal) {
        model.addAttribute("attendances", attendanceService.getAttendanceScheduleForToday(principal.getName()));
        return "attendances";
    }

    @GetMapping("/attendance/date")
    public String listCustomersByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date";
    }

    @GetMapping("/attendance/new")
    public String createAttendanceForm(Model model) {
        AttendanceScheduleDto attendance = new AttendanceScheduleDto();
        model.addAttribute("attendance", attendance);
        return "create_attendance";
    }

//    @GetMapping(value = "/attendance/today")
//    public List<AttendanceScheduleDto> getAttendanceForToday(@ModelAttribute("attendances") AttendanceScheduleDto attendance, Principal principal) {
//        return attendanceService.getAttendanceScheduleForToday(principal.getName());
//    }

    @GetMapping(value = "/attendance/byDate")
    public String getAttendanceByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto, Model model, Principal principal) {
        model.addAttribute("attendances", attendanceService.getAttendanceScheduleForDate(LocalDateTime.parse(attendanceRequestDto.getStartDate()), LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName()));
        return "attendances";
    }

    @PostMapping(value = "/attendance")
    public String addAttendance(@ModelAttribute("attendance") AttendanceScheduleDto attendance, Model model, Principal principal) {
        attendanceService.saveAttendance(attendance);
        model.addAttribute("attendances", attendanceService.getAttendanceScheduleForToday(principal.getName()));
        return "attendances";
    }

    @GetMapping("/attendance/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, @NotNull Model model) {
        model.addAttribute("attendance", attendanceService.getAttendanceScheduleById(id));
        return "edit_attendance";
    }

    @PostMapping("/attendance/{id}")
    public String updateAttendance(@PathVariable Long id,
                                   @ModelAttribute("attendance") AttendanceScheduleDto attendanceScheduleDto, Principal principal, Model model) {
        attendanceService.updateAttendance(attendanceScheduleDto, id);
        model.addAttribute("attendances", attendanceService.getAttendanceScheduleForToday(principal.getName()));
        return "attendances";
    }

    @GetMapping("/attendance/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model, Principal principal) {
        attendanceService.deleteAttendanceById(id);
        model.addAttribute("attendances", attendanceService.getAttendanceScheduleForToday(principal.getName()));
        return "attendances";
    }

    @GetMapping(value = "/finance/today")
    public Double getFinanceForToday(Principal principal) {
        return attendanceService.getFinance(attendanceService.getAttendanceScheduleForToday(principal.getName()));
    }

    @GetMapping(value = "/finance/byDate")
    public Double getFinanceForByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto, Principal principal) {
        return attendanceService.getFinance(
                attendanceService.getAttendanceScheduleForDate(LocalDateTime.parse(attendanceRequestDto.getStartDate()), LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName()));
    }


}
