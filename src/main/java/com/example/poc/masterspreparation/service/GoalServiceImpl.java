package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.FinanceDto;
import com.example.poc.masterspreparation.dto.GoalDto;
import com.example.poc.masterspreparation.dto.GoalReviewDto;
import com.example.poc.masterspreparation.model.Goal;
import com.example.poc.masterspreparation.repository.CustomerRepository;
import com.example.poc.masterspreparation.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl {

    private final GoalRepository goalRepository;
    private final AttendanceService attendanceService;
    private final CustomerRepository customerRepository;

    public List<GoalDto> getAllGoalsByEmail(String email) {
        return goalRepository.findAll().stream()
                .filter(goal -> goal.getEmployee().getEmail().equals(email))
                .map(this::toFinanceDto)
                .collect(Collectors.toList());
    }

    public List<GoalReviewDto> getGoalReviews (LocalDateTime start, LocalDateTime finish, String email){
        List<GoalReviewDto> goalReviewDtos = new ArrayList<>();
    Set<FinanceDto> financeDtos = attendanceService.
            getFinancePlus(attendanceService.getAttendanceScheduleForDate(start, finish, email));

    List<GoalDto> goalDtos = getAllGoalsByEmail(email);

    for (GoalDto goal : goalDtos) {

       Optional<FinanceDto> financeDto = financeDtos.stream()
                .filter(finance -> finance.getRevenueName().equals(goal.getRevenueName()))
               .findFirst();

       if (financeDto.isPresent()){
           int sum = (financeDto.get().getTotalPrice()*100)/goal.getTotalPrice();
           GoalReviewDto goalReviewDto = new GoalReviewDto();
           goalReviewDto.setName(goal.getRevenueName());
           goalReviewDto.setGoal(goal.getTotalPrice());
           goalReviewDto.setComment("Got "+ sum  + " %");

           goalReviewDtos.add(goalReviewDto);

       }
    }
    return goalReviewDtos;

    }

    private GoalDto toFinanceDto(Goal goal) {
        return new GoalDto(goal.getId(), goal.getName(), goal.getSum());
    }

    public void saveGoal(GoalDto goalDto, String name) {
        Goal goal = new Goal();
        goal.setSum(goalDto.getTotalPrice());
        goal.setName(goalDto.getRevenueName());
        goal.setEmployee(customerRepository.findByEmail(name));

        goalRepository.save(goal);

    }
}
