package com.dacloud.pgw.auth.services.dtos.authClient;

import com.dacloud.pgw.auth.entities.AuthClient;
import com.dacloud.pgw.auth.entities.helpers.AuthClientStatus;
import com.dacloud.pgw.auth.entities.helpers.AuthClientType;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
public record AuthClientDTO(
      UUID id,
      String label,
      AuthClientType clientType,
      String description,
      String secret,
      AuthClientStatus status,
      Date createdAt
) {
   private static final String AUTH_CLIENT_DTO_LOG = "AuthClientDTO: {}";

   public static AuthClientDTO fromEntity(AuthClient entity) {
      return new AuthClientDTO(
            entity.getId(),
            entity.getLabel(),
            entity.getClientType(),
            entity.getDescription(),
            entity.getSecret(),
            entity.getStatus(),
            entity.getCreatedOn()
      );
   }
}
