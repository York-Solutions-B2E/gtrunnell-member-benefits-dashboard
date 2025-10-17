package com.greggtrunnelldashboard.backend.controllers;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PrivateController {

    private final JwtDecoder jwtDecoder;

    public PrivateController(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @GetMapping("/private")
    public Map<String, Object> getPrivate(@CookieValue("auth-token") String token) {
        Jwt jwt = jwtDecoder.decode(token);

        return Map.of(
                "message", "Hello from PRIVATE endpoint!",
                "email", jwt.getClaimAsString("email"),
                "sub", jwt.getSubject(),
                "issuer", jwt.getIssuer().toString()
        );
    }
}
