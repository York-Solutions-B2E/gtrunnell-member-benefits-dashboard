package com.greggtrunnelldashboard.backend.SeedData;

import com.greggtrunnelldashboard.backend.entities.*;
import com.greggtrunnelldashboard.backend.enums.*;
import com.greggtrunnelldashboard.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeedData {

    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AccumulatorRepository accumulatorRepository;
    private final ProviderRepository providerRepository;
    private final ClaimRepository claimRepository;
    private final ClaimLineRepository claimLineRepository;

    public Member createMember(User user, String name) {

        // 1️⃣ Member
        Member member = new Member();
        member.setUser(user);
        member.setFirstName(name.split(" ")[0]);
        member.setLastName(name.split(" ").length > 1 ? name.split(" ")[1] : "Member");
        member.setDateOfBirth(LocalDate.of(1990, 5, 12));
        member.setEmail(user.getEmail());
        member.setPhone("555-1234");
        memberRepository.save(member);

        // 2️⃣ Plan
        Plan plan = new Plan();
        plan.setPlanName("Gold PPO");
        plan.setPlanType(PlanType.PPO);
        plan.setNetworkName("Prime Network");
        plan.setPlanYear(2025);
        planRepository.save(plan);

        // 3️⃣ Enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setMember(member);
        enrollment.setPlan(plan);
        enrollment.setCoverageStart(LocalDate.of(2025, 1, 1));
        enrollment.setCoverageEnd(LocalDate.of(2025, 12, 31));
        enrollment.setActive(true);
        enrollmentRepository.save(enrollment);

        // 4️⃣ Accumulators (Deductible + OOP)
        Accumulator deductible = new Accumulator();
        deductible.setEnrollment(enrollment);
        deductible.setType(AccumulatorType.DEDUCTIBLE);
        deductible.setTier(NetworkTier.IN_NETWORK);
        deductible.setLimitAmount(BigDecimal.valueOf(1500));
        deductible.setUsedAmount(BigDecimal.valueOf(300));

        Accumulator oop = new Accumulator();
        oop.setEnrollment(enrollment);
        oop.setType(AccumulatorType.OOP_MAX);
        oop.setTier(NetworkTier.IN_NETWORK);
        oop.setLimitAmount(BigDecimal.valueOf(6000));
        oop.setUsedAmount(BigDecimal.valueOf(1200));

        accumulatorRepository.saveAll(List.of(deductible, oop));

        // 5️⃣ Provider
        Provider provider = new Provider();
        provider.setProviderName("River Clinic");
        provider.setProviderSpeciality("Primary Care");
        providerRepository.save(provider);

        // 6️⃣ Claim
        Claim claim = new Claim();
        claim.setClaimNumber("C-10421");
        claim.setMember(member);
        claim.setProvider(provider);
        claim.setServiceStartDate(LocalDate.of(2025, 8, 29));
        claim.setServiceEndDate(LocalDate.of(2025, 8, 29));
        claim.setReceivedDate(LocalDate.of(2025, 9, 2));
        claim.setStatus(ClaimStatus.PROCESSED);
        claim.setTotalBilled(BigDecimal.valueOf(300));
        claim.setTotalAllowed(BigDecimal.valueOf(200));
        claim.setTotalPlanPaid(BigDecimal.valueOf(155));
        claim.setTotalMemberResponsibility(BigDecimal.valueOf(45));
        claimRepository.save(claim);

        // 7️⃣ Claim Lines (for Claim Detail)
        ClaimLine line1 = new ClaimLine();
        line1.setClaim(claim);
        line1.setLineNumber(1);
        line1.setCptCode("99213");
        line1.setDescription("Office Visit, Est. Patient");
        line1.setBilledAmount(BigDecimal.valueOf(150));
        line1.setAllowedAmount(BigDecimal.valueOf(100));
        line1.setDeductibleApplied(BigDecimal.ZERO);
        line1.setCopayApplied(BigDecimal.valueOf(25));
        line1.setCoinsuranceApplied(BigDecimal.valueOf(10));
        line1.setPlanPaid(BigDecimal.valueOf(60));
        line1.setMemberResponsibility(BigDecimal.valueOf(15));
        claimLineRepository.save(line1);

        return member;
    }
}
