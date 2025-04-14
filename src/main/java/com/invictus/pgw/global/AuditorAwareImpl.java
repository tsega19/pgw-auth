package com.dacloud.pgw.global;

import com.dacloud.pgw.auth.entities.AuthClient;
import com.dacloud.pgw.auth.entities.AuthUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

   @Override
   public Optional<String> getCurrentAuditor() {
      final var authentication = SecurityContextHolder.getContext().getAuthentication();
      final var principal = authentication.getPrincipal();

      if (principal instanceof String)
         return Optional.of("anon");
      
      if (principal instanceof AuthClient)
         return Optional.of(((AuthClient) principal).getId().toString());

      return Optional.of(((AuthUser) principal).getEmail());
   }
}