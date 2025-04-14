package com.dacloud.pgw.auth.services.dtos.authClient;

import java.util.List;

public record GetMyAuthClientsDTO(
      int length,
      List<AuthClientDTO> clients
) {
}
