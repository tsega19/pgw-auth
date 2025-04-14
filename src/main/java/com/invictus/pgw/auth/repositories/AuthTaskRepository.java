package com.dacloud.pgw.auth.repositories;

import com.dacloud.pgw.auth.entities.AuthTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthTaskRepository extends JpaRepository<AuthTask, UUID> {
}
