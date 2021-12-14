package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.dto.*;
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
    public String listCustomers(Model model,
                                @ModelAttribute("price") TotalPriceDto totalPriceDto,
                                Principal principal) {
        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleForToday(principal.getName());


        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));
        model.addAttribute("attendances", attendanceScheduleForToday);
        return "attendances";
    }

    @GetMapping("/attendance/date")
    public String listCustomersByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date-for-attendance";
    }

    @GetMapping("/finance/date")
    public String FinancesByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date-for-finance";
    }

    @GetMapping("/statistic/date")
    public String StatisticByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date-for-statistic";
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
    public String getAttendanceByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto,
                                      @ModelAttribute("price") TotalPriceDto totalPriceDto, Model model, Principal principal) {
        List<AttendanceScheduleDto> attendanceScheduleForDate = attendanceService.getAttendanceScheduleForDate
                (LocalDateTime.parse(attendanceRequestDto.getStartDate()), LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName());
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForDate));
        model.addAttribute("attendances", attendanceScheduleForDate);
        return "attendances";
    }

    @PostMapping(value = "/attendance")
    public String addAttendance(@ModelAttribute("attendance") AttendanceScheduleDto attendance,
                                @ModelAttribute("price") TotalPriceDto totalPriceDto,
                                Model model, Principal principal) {
        attendanceService.saveAttendance(attendance);

        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleForToday(principal.getName());
        model.addAttribute("attendances", attendanceScheduleForToday);
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));
        return "attendances";
    }

    @GetMapping("/attendance/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, @NotNull Model model) {
        model.addAttribute("attendance", attendanceService.getAttendanceScheduleById(id));
        return "edit_attendance";
    }

    @PostMapping("/attendance/{id}")
    public String updateAttendance(@PathVariable Long id,
                                   @ModelAttribute("attendance") AttendanceScheduleDto attendanceScheduleDto,
                                   @ModelAttribute("price") TotalPriceDto totalPriceDto, Principal principal, Model model) {
        attendanceService.updateAttendance(attendanceScheduleDto, id);
        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleForToday(principal.getName());
        model.addAttribute("attendances", attendanceScheduleForToday);
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));

        return "attendances";
    }

    @GetMapping("/attendance/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model,
                                 @ModelAttribute("price") TotalPriceDto totalPriceDto,
                                 Principal principal) {
        attendanceService.deleteAttendanceById(id);
        List<AttendanceScheduleDto> attendanceScheduleForToday = attendanceService.getAttendanceScheduleForToday(principal.getName());
        model.addAttribute("attendances", attendanceScheduleForToday);
        model.addAttribute("price", attendanceService.getTotalPrice(attendanceScheduleForToday));
        return "attendances";
    }

//    @GetMapping(value = "/finance/today")
//    public Integer getFinanceForToday(Principal principal) {
//        return attendanceService.getFinance(attendanceService.getAttendanceScheduleForToday(principal.getName()));
//    }

    @GetMapping(value = "/finance/byDate")
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

    @GetMapping(value = "/statistic/byDate")
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
