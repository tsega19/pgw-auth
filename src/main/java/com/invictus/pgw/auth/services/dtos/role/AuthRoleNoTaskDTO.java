package com.dacloud.pgw.auth.services.dtos.role;

import com.dacloud.pgw.auth.entities.AuthRole;

public record AuthRoleNoTaskDTO(
      long id,
      String name,
      String description
) {

   public static AuthRoleNoTaskDTO fromEntity(AuthRole entity) {
      return new AuthRoleNoTaskDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
      );
   }
}
