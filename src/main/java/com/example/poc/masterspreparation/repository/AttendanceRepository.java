package com.example.poc.masterspreparation.repository;

import com.example.poc.masterspreparation.model.AttendanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceSchedule, Long> {

    List<AttendanceSchedule> getAllByDate(Date date);
}
