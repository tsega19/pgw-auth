package com.dacloud.pgw.auth.services.dtos.authClient;

import com.dacloud.pgw.auth.entities.helpers.AuthClientStatus;

import java.util.UUID;

public record ChangeStatusAuthClienRequestDTO(
      UUID authClient,
      AuthClientStatus status
) {
}
