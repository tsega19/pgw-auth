package com.dacloud.pgw.auth.services;

import com.dacloud.pgw.auth.entities.AuthRole;
import com.dacloud.pgw.auth.entities.AuthUser;
import com.dacloud.pgw.auth.repositories.AuthRoleRepository;
import com.dacloud.pgw.auth.repositories.AuthTaskRepository;
import com.dacloud.pgw.auth.services.dtos.role.*;
import com.dacloud.pgw.global.exceptions.ConflictException;
import com.dacloud.pgw.global.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthRoleService {
   final static Logger logger = LoggerFactory.getLogger(AuthRoleService.class);

   private final AuthRoleRepository authRoleRepository;
   private final AuthTaskRepository authTaskRepository;

   public GetAllRolesDTO getAllRoles() {
      final var roles = authRoleRepository.findAll();
      final var rolesDto = roles.stream().map(AuthRoleDTO::fromEntity).toList();

      return new GetAllRolesDTO(rolesDto.size(), rolesDto);
   }

   public AuthRoleNoTaskDTO createRole(AuthUser user, CreateRoleDTO createRoleDTO) {
      final var role = new AuthRole(createRoleDTO.name(), createRoleDTO.description());

      try {
         final var savedRole = authRoleRepository.save(role);
         return AuthRoleNoTaskDTO.fromEntity(savedRole);

      } catch (DataIntegrityViolationException e) {
         throw new ConflictException("Role '" + createRoleDTO.name() + "' already exists");
      }
   }

   public AuthRoleDTO addTasksToRole(Long id, AddTasksRequestDTO reqDTO) {
      final var role = authRoleRepository.findById(id).orElseThrow();

      final var newTasks = authTaskRepository.findAllById(reqDTO.tasks());

      final var oldTasks = role.getAllowedTasks();

      oldTasks.addAll(newTasks);

      role.setAllowedTasks(oldTasks);

      final var savedRole = authRoleRepository.save(role);

      return AuthRoleDTO.fromEntity(savedRole);
   }

   public AuthRoleDTO getARole(Long id) {
      logger.info("Getting role with id {}", id);

      final var _role = authRoleRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Role not found")
      );

      return AuthRoleDTO.fromEntity(_role);
   }
}
