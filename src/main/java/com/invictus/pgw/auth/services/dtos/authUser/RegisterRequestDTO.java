package com.dacloud.pgw.auth.services.dtos.authUser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDTO {

   @NotNull(message = "First name is required")
   @NotEmpty(message = "First name is required")
   public final String firstName;

   @NotEmpty(message = "Middle name is not required but can not be empty")
   public final String middleName;

   @NotNull(message = "Last name is required")
   @NotEmpty(message = "Last name is required")
   public final String lastName;

   @NotNull(message = "Email is required")
   @NotEmpty(message = "Email is required")
   public final String email;

   @NotNull(message = "Password is required")
   @NotEmpty(message = "Password is required")
   public final String password;
}
