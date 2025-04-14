package com.dacloud.pgw.auth.services;

import com.dacloud.pgw.auth.entities.AuthTask;
import com.dacloud.pgw.auth.repositories.AuthTaskRepository;
import com.dacloud.pgw.auth.services.dtos.task.AuthTaskDTO;
import com.dacloud.pgw.auth.services.dtos.task.CreateAuthTaskDTO;
import com.dacloud.pgw.auth.services.dtos.task.GetAllAuthTasksDTO;
import com.dacloud.pgw.global.exceptions.ConflictException;
import com.dacloud.pgw.global.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthTaskService {
   static final Logger logger = LoggerFactory.getLogger(AuthTaskService.class);

   private final AuthTaskRepository authTaskRepository;

   public AuthTaskDTO getAuthTask(UUID id) {
      logger.info("Getting auth task with id: {}", id);
      final var task = authTaskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Auth task not found"));

      return AuthTaskDTO.fromEntity(task);
   }

   public GetAllAuthTasksDTO getAllAuthTasks() {
      logger.info("Getting all auth tasks");
      final var tasks = authTaskRepository.findAll();
      logger.debug("Tasks found: {}", tasks.size());
      final var tasksDto = tasks.stream().map(AuthTaskDTO::fromEntity).toList();

      return new GetAllAuthTasksDTO(tasksDto.size(), tasksDto);
   }

   public AuthTaskDTO createAuthTask(CreateAuthTaskDTO createAuthTaskDTO) {
      logger.info("Creating auth task: {}", createAuthTaskDTO);

      final var task = new AuthTask(createAuthTaskDTO.target());

      try {
         final var savedTask = authTaskRepository.save(task);
         return AuthTaskDTO.fromEntity(savedTask);
      } catch (DataIntegrityViolationException e) {
         logger.error("Task with target {} already exists", createAuthTaskDTO.target());
         throw new ConflictException("Task with target " + createAuthTaskDTO.target() + " already exists");
      }
   }
}
