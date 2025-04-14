package com.dacloud.pgw.global.exceptions;

public class NotAuthorizedException extends RuntimeException {
   public NotAuthorizedException() {
      super();
   }

   public NotAuthorizedException(String message) {
      super(message);
   }
}
