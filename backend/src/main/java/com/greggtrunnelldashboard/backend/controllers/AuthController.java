package com.greggtrunnelldashboard.backend.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public AuthController(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        String googleToken = body.get("credential");

        // ✅ Optionally verify Google token (skipped for dev)
        Jwt googleJwt = jwtDecoder.decode(googleToken);

        // ✅ Issue your own internal short-lived token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(googleJwt.getSubject())
                .claim("email", googleJwt.getClaim("email"))
                .issuer("gt-dashboard")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();

        String internalToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        // ✅ Set the cookie (not visible to JS)
        ResponseCookie cookie = ResponseCookie.from("auth-token", internalToken)
                .httpOnly(true)
                .secure(false) // keep false for localhost
                .sameSite("Lax") // use "None" if you have stricter frontend setups
                .maxAge(Duration.ofHours(1))
                .path("/")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
