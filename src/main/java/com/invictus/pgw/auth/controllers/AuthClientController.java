package com.dacloud.pgw.auth.controllers;

import com.dacloud.pgw.auth.entities.AuthUser;
import com.dacloud.pgw.auth.services.AuthClientService;
import com.dacloud.pgw.auth.services.dtos.authClient.AuthClientDTO;
import com.dacloud.pgw.auth.services.dtos.authClient.ChangeStatusAuthClienRequestDTO;
import com.dacloud.pgw.auth.services.dtos.authClient.CreateAuthClientRequestDTO;
import com.dacloud.pgw.auth.services.dtos.authClient.GetMyAuthClientsDTO;
import com.dacloud.pgw.global.controllers.dtos.APIResponse;
import com.dacloud.pgw.global.controllers.validators.ValidUUID;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth/clients")
@Tags(value = {
      @Tag(name = "Auth Client", description = "Auth Client API")
})
@RequiredArgsConstructor
public class AuthClientController {
   static final Logger logger = LoggerFactory.getLogger(AuthClientController.class);

   private final AuthClientService authClientService;

   @PostMapping("/create_auth_client")
   public ResponseEntity<APIResponse<AuthClientDTO>> createAuthClient(
         @AuthenticationPrincipal AuthUser authUser,
         @Valid @RequestBody CreateAuthClientRequestDTO requestDTO) {
      return APIResponse.build(
            HttpStatus.CREATED.value(),
            "Auth Client Created successfully",
            authClientService.createAuthClient(authUser, requestDTO)
      );
   }

   @GetMapping("/get_my_auth_clients")
   public ResponseEntity<APIResponse<GetMyAuthClientsDTO>> getMyAuthClients(
         @AuthenticationPrincipal AuthUser authUser) {
      return APIResponse.build(
            HttpStatus.OK.value(),
            "Auth Clients fetched successfully",
            authClientService.getMyAuthClients(authUser)
      );
   }

   @GetMapping("/get_auth_clients/{auth_user_id}")
   public ResponseEntity<APIResponse<GetMyAuthClientsDTO>> getAuthClients(
         @PathVariable @Validated @ValidUUID String auth_user_id) {

      return APIResponse.build(
            HttpStatus.OK.value(),
            "Auth Clients fetched successfully",
            authClientService.getAuthClients(UUID.fromString(auth_user_id))
      );
   }

   @DeleteMapping("/remove_my_auth_client/{authClientId}")
   public ResponseEntity<APIResponse<Object>> removeMyAuthClient(
         @AuthenticationPrincipal AuthUser authUser, @PathVariable UUID authClientId) {
      authClientService.removeMyAuthClient(authUser, authClientId);
      return APIResponse.build(
            HttpStatus.OK.value(),
            "Auth Client removed successfully",
            null
      );
   }

   @PutMapping("/change_status")
   public ResponseEntity<APIResponse<AuthClientDTO>> changeStatus(
         @AuthenticationPrincipal AuthUser authUser, @RequestBody ChangeStatusAuthClienRequestDTO requestDTO) {
      return APIResponse.build(
            HttpStatus.OK.value(),
            "Auth Client status changed successfully",
            authClientService.changeStatus(authUser, requestDTO)
      );
   }
}
