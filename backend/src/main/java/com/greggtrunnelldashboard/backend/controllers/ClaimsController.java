package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.dto.ClaimDetailDTO;
import com.greggtrunnelldashboard.backend.dto.ClaimsListDTO;
import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.services.AuthService;
import com.greggtrunnelldashboard.backend.services.ClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimsController {

    private final AuthService authService;
    private final ClaimService claimService;

    @GetMapping
    public ResponseEntity<Page<ClaimsListDTO>> getClaims(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String claimNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Member member = authService.getOrCreateMemberFromJwt(jwt);
        Page<ClaimsListDTO> dtoPage = claimService.getFilteredClaims(member, status, provider, claimNumber, page, size);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{claimNumber}")
    public ResponseEntity<ClaimDetailDTO> getClaimDetail(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String claimNumber
    ) {
        Member member = authService.getOrCreateMemberFromJwt(jwt);
        ClaimDetailDTO detail = claimService.getClaimDetail(member, claimNumber);
        return ResponseEntity.ok(detail);
    }
}
