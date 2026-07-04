package com.schoolapp.auth.repository;

import com.schoolapp.auth.entity.Role;
import com.schoolapp.auth.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}