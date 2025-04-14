package com.dacloud.pgw.auth.entities.helpers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.dacloud.pgw.global.exceptions.EnumIllegalArgumentException;

import java.util.Arrays;

public enum AuthClientType {
   TEST("TEST"),
   PROD("PROD");


   @JsonValue
   private final String value;

   AuthClientType(String value) {
      this.value = value;
   }

   @JsonCreator
   public static AuthClientType fromValue(String value) {
      for (var _type : values())
         if (_type.value.equals(value))
            return _type;

      throw new EnumIllegalArgumentException(
            "Auth Client type",
            value,
            Arrays.toString(AuthClientType.values())
      );
   }
}
