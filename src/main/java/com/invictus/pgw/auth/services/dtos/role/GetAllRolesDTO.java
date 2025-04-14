package com.dacloud.pgw.auth.services.dtos.role;

import java.util.List;

public record GetAllRolesDTO(
      long length,
      List<AuthRoleDTO> roles
) {
}
