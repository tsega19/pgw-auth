package com.dacloud.pgw.global.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties("pgw-param")
public record KispayProperties(
      String pgwIp,
      String pgwPort,
      String checkoutUrl,
      Set<BankProperties> banks
) {
}

record BankProperties(
      String name,
      String location,
      String id
) {
   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
         return false;
      }
      BankProperties other = (BankProperties) obj;
      return id.equalsIgnoreCase(other.id);
   }
}
