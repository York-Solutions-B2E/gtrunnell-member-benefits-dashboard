package com.greggtrunnelldashboard.backend.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/api/public/hello")
    public String publicHello() {

        return "Hello Public!";
    }
    @GetMapping("/api/private/hello")
    public Map<String, Object> privateHello(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "message", "Hello from PRIVATE endpoint!",
                "email", jwt.getClaimAsString("email"),
                "sub", jwt.getClaimAsString("sub"),
                "issuer", jwt.getIssuer().toString()
        );
    }
}
