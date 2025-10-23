package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.SeedData.SeedData;
import com.greggtrunnelldashboard.backend.dto.MemberDTO;
import com.greggtrunnelldashboard.backend.entities.*;
import com.greggtrunnelldashboard.backend.repositories.*;
import com.greggtrunnelldashboard.backend.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final DashboardService dashboardService;
    private final SeedData seedData;

    @GetMapping
    public ResponseEntity<MemberDTO> getDashboard(@AuthenticationPrincipal Jwt jwt) {
        //  extracts OIDC info from token
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        String sub = jwt.getClaim("sub");
        String provider = jwt.getClaimAsString("iss"); // issuer (e.g. Google)

        User user = userRepository.findByAuthProviderAndAuthSub(provider, sub)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAuthProvider(provider != null ? provider : "google");
                    newUser.setAuthSub(sub);
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });

        //create Member
        Member member = memberRepository.findByUser(user)
                .orElseGet(() -> seedData.createMember(user, name));

        // build and return the MemberDTO
        MemberDTO dto = dashboardService.buildDashboardForMember(member);
        return ResponseEntity.ok(dto);
    }
}
