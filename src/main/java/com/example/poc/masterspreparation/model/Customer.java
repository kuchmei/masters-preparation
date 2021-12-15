package com.example.poc.masterspreparation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Entity
@Data
@Table(name = "Customer")
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @Pattern(regexp ="/^[a-zA-Z0-9_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$/",
            message ="{invalid.email}")
    private String email;

    @Column()
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Collection<AttendanceSchedule> customerSchedules;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Collection<AttendanceSchedule> workerSchedulesSchedules;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Collection<Goal> workerGoals;

    private boolean isAdmin;
}
