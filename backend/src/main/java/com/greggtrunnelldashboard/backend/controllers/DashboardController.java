package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.dto.MemberDTO;
import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.services.AuthService;
import com.greggtrunnelldashboard.backend.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final AuthService authService;
    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<MemberDTO> getDashboard(@AuthenticationPrincipal Jwt jwt) {
        Member member = authService.getOrCreateMemberFromJwt(jwt);
        MemberDTO dto = dashboardService.buildDashboardForMember(member);
        return ResponseEntity.ok(dto);
    }
}
