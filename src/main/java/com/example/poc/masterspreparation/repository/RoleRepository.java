package com.example.poc.masterspreparation.repository;

import com.example.poc.masterspreparation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
