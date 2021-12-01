package com.example.poc.masterspreparation.repository;

import com.example.poc.masterspreparation.model.FinanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceRepository extends JpaRepository<FinanceSchedule, Long> {
}
