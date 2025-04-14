package com.dacloud.pgw.auth.entities.helpers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.dacloud.pgw.global.exceptions.EnumIllegalArgumentException;

import java.util.Arrays;

public enum AuthClientStatus {
   INACTIVE("INACTIVE"),
   ACTIVE("ACTIVE");

   @JsonValue
   private final String value;

   AuthClientStatus(String value) {
      this.value = value;
   }

   @JsonCreator
   public static AuthClientStatus fromValue(String value) {
      for (var status : values())
         if (status.value.equals(value))
            return status;

      throw new EnumIllegalArgumentException(
            "Auth Client status",
            value,
            Arrays.toString(AuthClientStatus.values())
      );
   }
}
