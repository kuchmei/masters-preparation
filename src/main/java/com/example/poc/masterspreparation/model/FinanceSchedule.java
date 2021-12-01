package com.example.poc.masterspreparation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "finance")
@Data
public class FinanceSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private Double sum;

    private Double materials;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


}

