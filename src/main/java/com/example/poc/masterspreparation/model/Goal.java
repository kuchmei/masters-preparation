package com.example.poc.masterspreparation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Goal")
@NoArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer sum;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer employee;


}
