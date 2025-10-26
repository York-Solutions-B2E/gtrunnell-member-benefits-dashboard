package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.dto.DashboardDTO;
import com.greggtrunnelldashboard.backend.entities.*;
import com.greggtrunnelldashboard.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EnrollmentRepository enrollmentRepository;
    private final AccumulatorRepository accumulatorRepository;
    private final ClaimRepository claimRepository;

    public DashboardDTO buildDashboardForMember(Member member) {
        Enrollment enrollment = enrollmentRepository.findFirstByMemberAndActiveTrue(member)
                .orElseThrow(() -> new IllegalStateException("No active enrollment found"));

        List<Accumulator> accumulators = accumulatorRepository.findByEnrollmentId(enrollment.getId());
        List<Claim> claims = claimRepository.findTop5ByMemberIdOrderByReceivedDateDesc(member.getId());

        return DashboardDTO.from(member, enrollment, accumulators, claims);
    }
}
