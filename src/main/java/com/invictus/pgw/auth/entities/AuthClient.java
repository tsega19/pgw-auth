package com.dacloud.pgw.auth.entities;

import com.dacloud.pgw.auth.entities.helpers.AuthClientStatus;
import com.dacloud.pgw.auth.entities.helpers.AuthClientType;
import com.dacloud.pgw.global.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Entity(name = "auth_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthClient extends Auditable implements UserDetails {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column
   private String label;

   @Column
   private String description;

   @Column(unique = true)
   private String secret;

   @Enumerated(EnumType.STRING)
   private AuthClientType clientType;

   @Enumerated(EnumType.STRING)
   private AuthClientStatus status;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "owner_id")
   private AuthUser owner;

   public AuthClient(String label, String description, String secret, AuthUser owner, AuthClientType clientType) {
      this.label = label;
      this.description = description;
      this.secret = secret;
      this.owner = owner;
      this.clientType = clientType;
      this.status = AuthClientStatus.ACTIVE;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority("auth_client"));
   }

   @Override
   public String getPassword() {
      return "";
   }

   @Override
   public String getUsername() {
      return id.toString();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
