package com.dacloud.pgw.auth.controllers;

import com.dacloud.pgw.auth.entities.AuthUser;
import com.dacloud.pgw.auth.services.AuthRoleService;
import com.dacloud.pgw.auth.services.dtos.role.*;
import com.dacloud.pgw.global.controllers.dtos.APIResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/roles")
@RequiredArgsConstructor
public class AppRoleController {
   private static final Logger logger = LoggerFactory.getLogger(AppRoleController.class);

   final AuthRoleService roleService;

   @Deprecated
   @GetMapping(produces = "application/json")
   public ResponseEntity<APIResponse<GetAllRolesDTO>> _getAllRoles() {
      try {

         return APIResponse.build(
               HttpStatus.OK.value(),
               "Roles fetched successfully",
               roleService.getAllRoles()
         );
      } catch (Exception e) {
         return APIResponse.build(
               HttpStatus.BAD_REQUEST.value(),
               e.getMessage(),
               null
         );
      }
   }

   @GetMapping(value = "/get_all_roles", produces = "application/json")
   public ResponseEntity<APIResponse<GetAllRolesDTO>> getAllRoles() {
      logger.info("getAllRoles");

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Roles fetched successfully",
            roleService.getAllRoles()
      );
   }

   @PostMapping(produces = "application/json")
   public ResponseEntity<APIResponse<AuthRoleNoTaskDTO>> _createRole(
         @AuthenticationPrincipal AuthUser user, @RequestBody CreateRoleDTO reqdto) {

      try {
         return APIResponse.build(
               HttpStatus.CREATED.value(),
               "Role created successfully",
               true,
               roleService.createRole(user, reqdto)
         );
      } catch (Exception e) {
         return APIResponse.build(
               HttpStatus.BAD_REQUEST.value(),
               e.getMessage(),
               null
         );
      }

   }

   @PostMapping(value = "/create_role", produces = "application/json")
   public ResponseEntity<APIResponse<AuthRoleNoTaskDTO>> createRole(
         @AuthenticationPrincipal AuthUser user, @RequestBody @Valid CreateRoleDTO reqdto) {
      logger.info("createRole");

      return APIResponse.build(
            HttpStatus.CREATED.value(),
            "Role created successfully",
            roleService.createRole(user, reqdto)
      );
   }

   @Deprecated
   @PostMapping(value = "/{id}/add_tasks", produces = "application/json")
   public ResponseEntity<APIResponse<AuthRoleDTO>> _addTasksToRole(
         @PathVariable Long id, @RequestBody AddTasksRequestDTO reqDTO) {

      try {
         return APIResponse.build(
               HttpStatus.OK.value(),
               "Tasks added successfully",
               roleService.addTasksToRole(id, reqDTO)
         );
      } catch (Exception e) {
         return APIResponse.build(
               HttpStatus.BAD_REQUEST.value(),
               e.getMessage(),
               null
         );
      }
   }

   @PostMapping(value = "/add_tasks/{auth_role_id}", produces = "application/json")
   public ResponseEntity<APIResponse<AuthRoleDTO>> addTasksToRole(
         @PathVariable Long auth_role_id, @RequestBody AddTasksRequestDTO reqDTO) {
      logger.info("addTasksToRole auth_role_id={}", auth_role_id);

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Tasks added successfully",
            roleService.addTasksToRole(auth_role_id, reqDTO)
      );
   }

   @GetMapping(value = "/get_a_role/{id}", produces = "application/json")
   public ResponseEntity<APIResponse<AuthRoleDTO>> getARole(
         @PathVariable
         @Positive(message = "ID must be a positive number")
         Long id) {
      logger.info("get_a_role path[id={}]", id);

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Role fetched successfully",
            roleService.getARole(id)
      );
   }

}
