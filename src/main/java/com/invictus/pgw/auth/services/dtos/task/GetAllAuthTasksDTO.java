package com.dacloud.pgw.auth.services.dtos.task;

import java.util.List;

public record GetAllAuthTasksDTO(
      long length,
      List<AuthTaskDTO> tasks
) {
}
