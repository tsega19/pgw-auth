package com.dacloud.pgw.global.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
   @GetMapping("/admin-only")
   public String adminOnly() {
      return "This is only for admins";
   }

   @GetMapping("/user-only")
   public String userOnly() {
      return "This is only for users: " + SecurityContextHolder.getContext().getAuthentication().getName();
   }
}
