package com.example.poc.masterspreparation.repository;

import com.example.poc.masterspreparation.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
