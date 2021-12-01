package com.example.poc.masterspreparation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "Customer")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column()
    private String password;

    @ManyToMany
    @JoinTable(name = "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private Collection<AttendanceSchedule> customerSchedules;

    @OneToMany(mappedBy = "worker")
    @JsonManagedReference
    private Collection<AttendanceSchedule> workerSchedulesSchedules;
}
