package com.dacloud.pgw.auth.services.dtos.role;

import com.dacloud.pgw.auth.entities.AuthRole;
import com.dacloud.pgw.auth.services.dtos.task.AuthTaskDTO;

import java.util.List;
import java.util.stream.Collectors;

public record AuthRoleDTO(
      long id,
      String name,
      String description,
      List<AuthTaskDTO> allowedTasks
) {

   public static AuthRoleDTO fromEntity(AuthRole entity) {
      return new AuthRoleDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getAllowedTasks()
                  .stream()
                  .map(AuthTaskDTO::fromEntity)
                  .collect(Collectors.toList())
      );
   }

   public static AuthRole toEntity(AuthRoleDTO dto) {
      return new AuthRole(
            dto.id(),
            dto.name(),
            dto.description(),
            dto.allowedTasks()
                  .stream()
                  .map(AuthTaskDTO::toEntity)
                  .collect(Collectors.toSet())
      );
   }
}
