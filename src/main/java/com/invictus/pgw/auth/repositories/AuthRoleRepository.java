package com.dacloud.pgw.auth.repositories;

import com.dacloud.pgw.auth.entities.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
   Optional<AuthRole> findByName(String name);
}
