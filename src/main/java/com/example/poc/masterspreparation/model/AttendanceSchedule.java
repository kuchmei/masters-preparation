package com.example.poc.masterspreparation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "attendance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String comment;

    private Double sum;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "customer_email")
    private Customer client;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer worker;

}
