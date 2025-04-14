package com.dacloud.pgw.global.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
      super(message);
   }
}
