package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.dto.DashboardDTO;
import com.greggtrunnelldashboard.backend.dto.RecentClaimDTO;
import com.greggtrunnelldashboard.backend.entities.Accumulator;
import com.greggtrunnelldashboard.backend.entities.Claim;
import com.greggtrunnelldashboard.backend.entities.Enrollment;
import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.repositories.AccumulatorRepository;
import com.greggtrunnelldashboard.backend.repositories.ClaimRepository;
import com.greggtrunnelldashboard.backend.repositories.EnrollmentRepository;
import com.greggtrunnelldashboard.backend.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MemberRepository memberRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AccumulatorRepository accumulatorRepository;
    private final ClaimRepository claimRepository;

    public DashboardDTO getDashboardForCurrentUser() {
        Member member = memberRepository.findByEmail("gregg@example.com").orElseThrow();
        Enrollment enrollment = enrollmentRepository.findByMember_IdAndActiveTrue(member.getId());
        List<Accumulator> accs = accumulatorRepository.findByEnrollmentId(enrollment.getId());
        List<Claim> claims = claimRepository.findByMemberId(member.getId());

        return new DashboardDTO(
                member.getFirstName() + " " + member.getLastName(),
                enrollment.getPlan().getPlanName(),
                enrollment.getPlan().getNetworkName(),
                enrollment.getPlan().getPlanYear(),
                accs.get(0).getUsedAmount(),
                accs.get(0).getLimitAmount(),
                accs.get(1).getUsedAmount(),
                accs.get(1).getLimitAmount(),
                claims.stream()
                        .map(RecentClaimDTO::from)
                        .collect(Collectors.toList())

        );
    }
}

