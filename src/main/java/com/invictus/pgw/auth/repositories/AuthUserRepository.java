package com.dacloud.pgw.auth.repositories;

import com.dacloud.pgw.auth.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
   Optional<AuthUser> findByEmail(String email);
}
