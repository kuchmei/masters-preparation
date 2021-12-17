package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.dto.GoalDto;
import com.example.poc.masterspreparation.dto.ScheduleRequestDto;
import com.example.poc.masterspreparation.service.GoalServiceImpl;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/goal")
public class GoalController {

    private final GoalServiceImpl goalService;

    @GetMapping("/date")
    public String GoalsByDate(Model model) {
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "create_request-by-date-for-goal-review";
    }

    @GetMapping(value = "/byDate")
    public String getGoalByDate(@ModelAttribute ScheduleRequestDto attendanceRequestDto,
                                Model model, Principal principal) {
        model.addAttribute("goals", goalService.getGoalReviews(LocalDateTime.parse(attendanceRequestDto.getStartDate()),
                LocalDateTime.parse(attendanceRequestDto.getFinishDate()), principal.getName()));
        return "goals-review";
    }


    @GetMapping(value = "/new")
    public String createGoal(Model model) {
        GoalDto financeDto = new GoalDto();
        model.addAttribute("goal", financeDto);
        return "create_goal";
    }

    @PostMapping()
    public String addAGoal(@ModelAttribute("goal") GoalDto goalDto,
                           Model model, Principal principal) {
        goalService.saveGoal(goalDto, principal.getName());

        model.addAttribute("goals", goalService.getAllGoalsByEmail(principal.getName()));

        return "goals";
    }

    @GetMapping()
    public String getGoals(Model model, Principal principal) {

        model.addAttribute("goals", goalService.getAllGoalsByEmail(principal.getName()));

        return "goals";
    }

    @GetMapping("/edit/{id}")
    public String editGoalForm(@PathVariable Long id, @NotNull Model model) {
        model.addAttribute("goal", goalService.getGoalDtoById(id));
        return "edit_goal";
    }

    @PostMapping("/{id}")
    public String updateGoal(@PathVariable Long id,
                             @ModelAttribute("goal") GoalDto goalDto,
                             Principal principal, Model model) {
        goalService.updateGoal(goalDto, id, principal.getName());
        model.addAttribute("goals", goalService.getAllGoalsByEmail(principal.getName()));

        return "goals";
    }

    @GetMapping("/{id}")
    public String deleteGoal(@PathVariable Long id, Model model,
                             Principal principal) {
        goalService.deleteGoalById(id);

        model.addAttribute("goals", goalService.getAllGoalsByEmail(principal.getName()));
        return "goals";
    }


}
