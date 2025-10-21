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
//    private final EnrollmentRepository enrollmentRepository;
//    private final AccumulatorRepository accumulatorRepository;
//    private final ClaimRepository claimRepository;
    private final SeedData seedData;

    @GetMapping
    public ResponseEntity<MemberDTO> getDashboard(@AuthenticationPrincipal Jwt jwt) {
        // 1️⃣ Extract OIDC info from token
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        String sub = jwt.getClaim("sub");
        String provider = jwt.getClaimAsString("iss"); // issuer (e.g. Google)

        // 2️⃣ Find or create User
        User user = userRepository.findByAuthProviderAndAuthSub(provider, sub)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAuthProvider(provider != null ? provider : "google");
                    newUser.setAuthSub(sub);
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });

        // 3️⃣ Find or create Member (auto-seed if new)
        Member member = memberRepository.findByUser(user)
                .orElseGet(() -> seedData.createMember(user, name));

//        // 4️⃣ Load related data
//        Enrollment enrollment = enrollmentRepository.findFirstByMemberAndActiveTrue(member)
//                .orElseThrow(() -> new IllegalStateException("No active enrollment found"));
//        List<Accumulator> accumulators = accumulatorRepository.findByEnrollmentId(enrollment.getId());
//        List<Claim> claims = claimRepository.findTop5ByMemberIdOrderByReceivedDateDesc(member.getId());
//
//        // 5️⃣ Assemble DTO
//        MemberDTO dto = new MemberDTO(member, enrollment, accumulators, claims);
//        return ResponseEntity.ok(dto);
//    }

        // Build and return the MemberDTO (the Dashboard view)
        MemberDTO dto = dashboardService.buildDashboardForMember(member);
        return ResponseEntity.ok(dto);
    }
}
