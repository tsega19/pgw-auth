package com.dacloud.pgw.auth.services.dtos.authClient;

import com.dacloud.pgw.auth.entities.helpers.AuthClientType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAuthClientRequestDTO(
      @Size(min = 5, max = 40, message = "The label should be between a length of 5 and 40")
      @NotNull(message = "The label is required")
      String label,
      String description,

      @NotNull(message = "Auth client type is required")
      AuthClientType clientType
) {
}
