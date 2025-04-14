package com.dacloud.pgw.auth.services;

import com.dacloud.pgw.auth.entities.AuthRole;
import com.dacloud.pgw.auth.entities.AuthUser;
import com.dacloud.pgw.auth.repositories.AuthRoleRepository;
import com.dacloud.pgw.auth.repositories.AuthUserRepository;
import com.dacloud.pgw.auth.services.dtos.authUser.AddRolesRequestDTO;
import com.dacloud.pgw.auth.services.dtos.authUser.AuthUserWithRoleOnlyDTO;
import com.dacloud.pgw.auth.services.dtos.authUser.GelAllUsersDTO;
import com.dacloud.pgw.auth.services.dtos.authUser.UpdateAuthUserRequestDTO;
import com.dacloud.pgw.global.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserService {
   static final Logger logger = LoggerFactory.getLogger(AuthUserService.class);

   private final AuthUserRepository authUserRepository;
   private final AuthRoleRepository authRoleRepository;

   public GelAllUsersDTO getAllUsers() {
      logger.info("Fetching all users");

      final var users = authUserRepository.findAll();

      final var temp = users
            .stream()
            .map(AuthUserWithRoleOnlyDTO::fromEntity)
            .toList();

      return new GelAllUsersDTO(temp.size(), temp);
   }

   public AuthUserWithRoleOnlyDTO addRolesToUser(UUID authUserId, AddRolesRequestDTO reqDTO) {
      logger.info("Adding roles {} to user {}", reqDTO.roles(), authUserId);

      final var user = authUserRepository.findById(authUserId)
            .orElseThrow(() -> new NotFoundException("Auth user not found"));

      final var roles = authRoleRepository.findAllById(reqDTO.roles());

      user.getRoles().addAll(roles);

      authUserRepository.save(user);

      return AuthUserWithRoleOnlyDTO.fromEntity(user);
   }

   public AuthUserWithRoleOnlyDTO removeRolesFromUser(UUID id, AddRolesRequestDTO reqDTO) {
      final var user = authUserRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Auth user not found"));

      final var roles = authRoleRepository.findAllById(reqDTO.roles());

      roles.forEach(user.getRoles()::remove);

      authUserRepository.save(user);

      return AuthUserWithRoleOnlyDTO.fromEntity(user);
   }

   public AuthUserWithRoleOnlyDTO updateAuthUserInformation(AuthUser authUser, UpdateAuthUserRequestDTO reqDTO) {
      final var updated = authUserRepository.save(reqDTO.updateEntity(authUser));

      return AuthUserWithRoleOnlyDTO.fromEntity(updated);
   }
}
