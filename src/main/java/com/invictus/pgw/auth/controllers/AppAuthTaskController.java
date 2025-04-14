package com.dacloud.pgw.auth.controllers;

import com.dacloud.pgw.auth.services.AuthTaskService;
import com.dacloud.pgw.auth.services.dtos.task.AuthTaskDTO;
import com.dacloud.pgw.auth.services.dtos.task.CreateAuthTaskDTO;
import com.dacloud.pgw.auth.services.dtos.task.GetAllAuthTasksDTO;
import com.dacloud.pgw.global.controllers.dtos.APIResponse;
import com.dacloud.pgw.global.controllers.validators.ValidUUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/tasks")
@RequiredArgsConstructor
public class AppAuthTaskController {
   static final Logger logger = LoggerFactory.getLogger(AppAuthTaskController.class);

   private final AuthTaskService authTaskService;

   @Deprecated
   @GetMapping(produces = "application/json")
   public ResponseEntity<APIResponse<GetAllAuthTasksDTO>> _getAllTasks() {
      try {
         return APIResponse.build(
               HttpStatus.OK.value(),
               "Tasks fetched successfully",
               authTaskService.getAllAuthTasks()
         );
      } catch (Exception e) {
         return APIResponse.build(
               HttpStatus.BAD_REQUEST.value(),
               e.getMessage(),
               null
         );
      }
   }

   @GetMapping(value = "/get_all_tasks", produces = "application/json")
   public ResponseEntity<APIResponse<GetAllAuthTasksDTO>> getAllTasks() {
      logger.info("Get all tasks");

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Tasks fetched successfully",
            authTaskService.getAllAuthTasks()
      );
   }

   @Deprecated
   @GetMapping(value = "/{id}", produces = "application/json")
   public ResponseEntity<APIResponse<AuthTaskDTO>> _getTask(@PathVariable UUID id) {
      try {
         return APIResponse.build(
               HttpStatus.OK.value(),
               "Task fetched successfully",
               authTaskService.getAuthTask(id)
         );
      } catch (NoSuchElementException e) {
         return APIResponse.build(
               HttpStatus.NOT_FOUND.value(),
               "Auth task not foung",
               null
         );
      } catch (Exception e) {
         e.printStackTrace();
         return APIResponse.build(
               HttpStatus.BAD_REQUEST.value(),
               e.getMessage(),
               null
         );
      }
   }

   @GetMapping(value = "/get_a_task/{task_id}", produces = "application/json")
   public ResponseEntity<APIResponse<AuthTaskDTO>> getATask(@PathVariable @Valid @ValidUUID String task_id) {
      logger.info("getATask {}", task_id);

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Task fetched successfully",
            authTaskService.getAuthTask(UUID.fromString(task_id))
      );
   }

   @Deprecated
   @PostMapping(produces = "application/json")
   public ResponseEntity<APIResponse<AuthTaskDTO>> createTask(@RequestBody CreateAuthTaskDTO reqdto) {
      try {
         return APIResponse.build(
               HttpStatus.CREATED.value(),
               "Task created successfully",
               authTaskService.createAuthTask(reqdto)
         );
      } catch (DataIntegrityViolationException e) {
         return APIResponse.build(
               HttpStatus.CONFLICT.value(),
               "Task with target " + reqdto.target() + " already exists",
               null
         );
      } catch (Exception e) {
         e.printStackTrace();
         return APIResponse.build(
               HttpStatus.BAD_REQUEST.value(),
               e.getMessage(),
               null
         );
      }
   }

   @PostMapping(value = "/create_a_task", produces = "application/json")
   public ResponseEntity<APIResponse<AuthTaskDTO>> createATask(@RequestBody CreateAuthTaskDTO reqdto) {
      logger.info("createATask ");

      return APIResponse.build(
            HttpStatus.CREATED.value(),
            "Task created successfully",
            authTaskService.createAuthTask(reqdto)
      );
   }
}
