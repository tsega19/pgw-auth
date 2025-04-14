package com.dacloud.pgw.auth.services;

import com.dacloud.pgw.auth.entities.AuthClient;
import com.dacloud.pgw.auth.entities.AuthUser;
import com.dacloud.pgw.auth.repositories.AuthClientRepository;
import com.dacloud.pgw.auth.services.dtos.authClient.AuthClientDTO;
import com.dacloud.pgw.auth.services.dtos.authClient.ChangeStatusAuthClienRequestDTO;
import com.dacloud.pgw.auth.services.dtos.authClient.CreateAuthClientRequestDTO;
import com.dacloud.pgw.auth.services.dtos.authClient.GetMyAuthClientsDTO;
import com.dacloud.pgw.global.exceptions.NotAuthorizedException;
import com.dacloud.pgw.global.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthClientService {
   static final Logger logger = LoggerFactory.getLogger(AuthClientService.class);

   public static final String choices = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
   public static final int cLength = choices.length();
   public static final int secretLength = 32;
   public static final String secretPrefix = "kpg";

   private static final Random random = new Random(System.currentTimeMillis());

   private final AuthClientRepository authClientRepository;

   public AuthClient _getAuthClient(UUID authClientId) {
      return authClientRepository.findById(authClientId)
            .orElseThrow(() -> new NotFoundException("Auth client not found"));
   }

   public AuthClient _getAuthClientBySecret(String clientSecret) {
      return authClientRepository.findBySecret(clientSecret)
            .orElseThrow(() -> new NotFoundException("Auth client not found"));
   }

   public String _generateClientSecret() {
      final var sb = new StringBuilder();
      for (var i = 0; i < secretLength; ++i) {
         sb.append(choices.charAt(random.nextInt(cLength)));
      }
      return sb.toString();
   }

   public AuthClientDTO createAuthClient(AuthUser authUser, CreateAuthClientRequestDTO requestDTO) {
      final var tempClient = new AuthClient(
            requestDTO.label(),
            requestDTO.description(),
            secretPrefix + "_" + _generateClientSecret(),
            authUser,
            requestDTO.clientType()
      );

      final var savedClient = authClientRepository.save(tempClient);

      return AuthClientDTO.fromEntity(savedClient);
   }

   public GetMyAuthClientsDTO getMyAuthClients(AuthUser authUser) {
      final var clients = authClientRepository.findByOwnerId(authUser.getId());
      return new GetMyAuthClientsDTO(
            clients.size(),
            clients.stream().map(AuthClientDTO::fromEntity).toList()
      );
   }

   public GetMyAuthClientsDTO getAuthClients(UUID auth_user_id) {
      logger.info("Fetching auth clients for auth_user_id: {}", auth_user_id);
      final var clients = authClientRepository.findByOwnerId(auth_user_id);

      logger.info("Fetched {} auth clients", clients.size());
      return new GetMyAuthClientsDTO(
            clients.size(),
            clients.stream().map(AuthClientDTO::fromEntity).toList()
      );
   }

   public void removeMyAuthClient(AuthUser authUser, UUID authClientId) {
      final var client = _getAuthClient(authClientId);

      if (!client.getOwner().getId().equals(authUser.getId()))
         throw new NotFoundException("Auth client not found");

      authClientRepository.delete(client);
   }

   public AuthClientDTO changeStatus(AuthUser authUser, ChangeStatusAuthClienRequestDTO requestDTO) {
      final var client = _getAuthClient(requestDTO.authClient());

      if (!client.getOwner().getId().equals(authUser.getId()))
         throw new NotAuthorizedException("You are not authorized");

      client.setStatus(requestDTO.status());

      final var temp = authClientRepository.save(client);

      return AuthClientDTO.fromEntity(temp);
   }
}
