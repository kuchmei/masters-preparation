package com.example.poc.masterspreparation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private Date date;

    private String comment;

    private Double sum;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "customer_email", nullable = false, insertable = false, updatable = false)
    private Customer client;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Customer worker;

}
