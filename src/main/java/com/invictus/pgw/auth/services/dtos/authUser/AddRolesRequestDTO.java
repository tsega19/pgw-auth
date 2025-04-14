package com.dacloud.pgw.auth.services.dtos.authUser;

import java.util.List;

public record AddRolesRequestDTO(
      List<Long> roles
) {
}
