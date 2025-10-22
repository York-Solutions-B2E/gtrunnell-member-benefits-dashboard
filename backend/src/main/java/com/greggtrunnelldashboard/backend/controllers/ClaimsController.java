package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.SeedData.SeedData;
import com.greggtrunnelldashboard.backend.dto.ClaimsListDTO;
import com.greggtrunnelldashboard.backend.entities.Claim;
import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.entities.User;
import com.greggtrunnelldashboard.backend.enums.ClaimStatus;
import com.greggtrunnelldashboard.backend.repositories.ClaimRepository;
import com.greggtrunnelldashboard.backend.repositories.MemberRepository;
import com.greggtrunnelldashboard.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimsController {

    private final ClaimRepository claimRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final SeedData seedData;

    @GetMapping
    public ResponseEntity<Page<ClaimsListDTO>> getClaims(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String claimNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // 🧩 Extract user identity
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        String sub = jwt.getClaim("sub");
        String providerId = jwt.getClaimAsString("iss");

        // 🧩 Find or create user + member
        User user = userRepository.findByAuthProviderAndAuthSub(providerId, sub)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAuthProvider(providerId != null ? providerId : "google");
                    newUser.setAuthSub(sub);
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });

        Member member = memberRepository.findByUser(user)
                .orElseGet(() -> seedData.createMember(user, name));

        // 🧩 Convert status to enum (safe)
        ClaimStatus claimStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                claimStatus = ClaimStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException ignored) {
                // silently ignore invalid filters
            }
        }
        String providerStr = (provider != null) ? new String(provider.getBytes()) : null;

        // 🧩 Run query
        PageRequest pageable = PageRequest.of(page, size, Sort.by("receivedDate").descending());
        log.info("Fetching claims for memberId={} | status={} | provider={} | claimNumber={}",
                member.getId(), claimStatus, provider, claimNumber);

        Page<Claim> claims;
        try {
            claims = claimRepository.findFiltered(
                    member.getId(), claimStatus, provider, claimNumber, pageable);
        } catch (Exception e) {
            log.error("🔥 Error fetching claims: {}", e.getMessage(), e);
            throw e; // rethrow so Spring still returns 500
        }

        // 🧩 Map to DTOs
        Page<ClaimsListDTO> dtoPage = claims.map(ClaimsListDTO::from);
        return ResponseEntity.ok(dtoPage);
    }
    @GetMapping("/{claimNumber}")
    public ResponseEntity<?> getClaimDetail(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String claimNumber
    ) {
        log.info("Fetching claim detail for claimNumber={}", claimNumber);

        String sub = jwt.getClaim("sub");
        String providerId = jwt.getClaimAsString("iss");

        User user = userRepository.findByAuthProviderAndAuthSub(providerId, sub)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Claim claim = claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new RuntimeException("Claim not found: " + claimNumber));

        // Security check — ensure this claim belongs to this member
        if (!claim.getMember().getId().equals(member.getId())) {
            log.warn("Unauthorized access: member {} tried to access claim {}", member.getId(), claimNumber);
            return ResponseEntity.status(403).body("You are not allowed to view this claim.");
        }

        // Return a lightweight DTO (next step)
        return ResponseEntity.ok(com.greggtrunnelldashboard.backend.dto.ClaimDetailDTO.from(claim));
    }

}
