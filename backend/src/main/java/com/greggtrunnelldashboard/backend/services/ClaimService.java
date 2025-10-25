package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.dto.ClaimsListDTO;
import com.greggtrunnelldashboard.backend.dto.ClaimDetailDTO;
import com.greggtrunnelldashboard.backend.entities.Claim;
import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.enums.ClaimStatus;
import com.greggtrunnelldashboard.backend.repositories.ClaimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;

    public Page<ClaimsListDTO> getFilteredClaims(Member member, String status, String provider, String claimNumber, int page, int size) {
        ClaimStatus claimStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                claimStatus = ClaimStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.by("receivedDate").descending());
        log.info("Fetching claims for memberId={} | status={} | provider={} | claimNumber={}",
                member.getId(), claimStatus, provider, claimNumber);

        Page<Claim> claims = claimRepository.findFiltered(
                member.getId(), claimStatus, provider, claimNumber, pageable);

        return claims.map(ClaimsListDTO::from);
    }

    public ClaimDetailDTO getClaimDetail(Member member, String claimNumber) {
        Claim claim = claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new RuntimeException("Claim not found: " + claimNumber));

        if (!claim.getMember().getId().equals(member.getId())) {
            log.warn("Unauthorized access: member {} tried to access claim {}", member.getId(), claimNumber);
            throw new RuntimeException("Unauthorized");
        }

        return ClaimDetailDTO.from(claim);
    }
}
