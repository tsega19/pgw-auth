package com.dacloud.pgw.auth.services.dtos.role;

import java.util.Set;
import java.util.UUID;

public record AddTasksRequestDTO(
      Set<UUID> tasks
) {
}
