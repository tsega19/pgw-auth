package com.dacloud.pgw.auth.services.dtos.authUser;

public record LoginRequestDTO(
      String email,
      String password
) {
}
