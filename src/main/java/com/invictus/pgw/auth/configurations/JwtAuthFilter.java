package com.dacloud.pgw.auth.configurations;

import com.dacloud.pgw.auth.entities.AuthUser;
import com.dacloud.pgw.auth.entities.AuthClient;
import com.dacloud.pgw.auth.entities.helpers.AuthClientStatus;
import com.dacloud.pgw.auth.services.AuthClientService;
import com.dacloud.pgw.auth.services.AuthUserDetailService;
import com.dacloud.pgw.auth.services.JwtUtils;
import com.dacloud.pgw.global.exceptions.NotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JwtUtils jwtUtils;
   private final AuthUserDetailService authUserDetailService;

   private final AuthClientService authClientService;

   @Override
   protected void doFilterInternal(
         HttpServletRequest request,
         HttpServletResponse response,
         FilterChain filterChain) throws ServletException, IOException {

      final String authClientSecret = request.getHeader("x-api-key");
      final var reqPath = request.getRequestURI();

      if (authClientSecret != null &&
            !authClientSecret.isBlank() &&
            (reqPath.equals("/api/checkout/create_checkout_session") ||
                  reqPath.startsWith("/api/checkout/get_checkout_session"))) {
         AuthClient authClient = null;

         try {
            authClient = authClientService._getAuthClientBySecret(authClientSecret);
         } catch (NotFoundException ignored) {
            filterChain.doFilter(request, response);
            return;
         }

         if (authClient != null && authClient.getStatus() == AuthClientStatus.ACTIVE) {
            final var securityContext = SecurityContextHolder.createEmptyContext();

            final var authToken = new UsernamePasswordAuthenticationToken(
                  authClient, null, authClient.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(authToken);

            SecurityContextHolder.setContext(securityContext);

            filterChain.doFilter(request, response);
            return;
         }
      }

      final String authHeader = request.getHeader("Authorization");

      if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response);
         return;
      }

      final var jwtToken = authHeader.substring(7);
      final var userEmail = jwtUtils.extractUsername(jwtToken);

      if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
         final var userDetails = authUserDetailService.loadUserByUsername(userEmail);

         // fetch the current request path to pass it to the authorise method

         if (authUserDetailService.authorise((AuthUser) userDetails, reqPath) && jwtUtils.isTokenValid(jwtToken, userDetails)) {
            final var securityContext = SecurityContextHolder.createEmptyContext();
            final var authToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(authToken);

            SecurityContextHolder.setContext(securityContext);
         }
      }

      filterChain.doFilter(request, response);
   }
}
