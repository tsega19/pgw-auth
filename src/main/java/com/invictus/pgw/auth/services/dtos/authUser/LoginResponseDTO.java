package com.dacloud.pgw.auth.services.dtos.authUser;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponseDTO(
      String token,
      String refreshToken,
      RegisterResponseDTO user
) {
}
