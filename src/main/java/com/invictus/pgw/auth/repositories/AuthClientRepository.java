package com.dacloud.pgw.auth.repositories;

import com.dacloud.pgw.auth.entities.AuthClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthClientRepository extends JpaRepository<AuthClient, UUID> {
   List<AuthClient> findByOwnerId(UUID ownerId);

   Optional<AuthClient> findBySecret(String secret);
}
