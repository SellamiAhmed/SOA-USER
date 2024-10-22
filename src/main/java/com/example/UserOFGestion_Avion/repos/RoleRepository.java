package com.example.UserOFGestion_Avion.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserOFGestion_Avion.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRole(String role);
}
