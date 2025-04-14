package com.dacloud.pgw.auth.services.dtos.authUser;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.dacloud.pgw.auth.entities.AuthRole;
import com.dacloud.pgw.auth.entities.AuthUser;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterResponseDTO(
      UUID id,
      String email,
      String firstName,
      String middleName,
      String lastName,
      List<String> roles
) {
   public static RegisterResponseDTO fromEntity(AuthUser entity) {
      return new RegisterResponseDTO(
            entity.getId(),
            entity.getEmail(),
            entity.getFirstName(),
            entity.getMiddleName(),
            entity.getLastName(),
            entity.getRoles() == null
                  ? List.of()
                  : entity.getRoles().stream().map(AuthRole::getName).toList()
      );
   }
}