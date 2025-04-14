package com.dacloud.pgw.auth.services.exceptions;

public class IncorrectInputException extends RuntimeException {
   public IncorrectInputException(String message) {
      super(message);
   }
}
