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

        // 5️⃣ Providers
        Provider riverClinic = new Provider();
        riverClinic.setProviderName("River Clinic");
        riverClinic.setProviderSpeciality("Primary Care");
        providerRepository.save(riverClinic);

        Provider cityImaging = new Provider();
        cityImaging.setProviderName("City Imaging Center");
        cityImaging.setProviderSpeciality("Radiology");
        providerRepository.save(cityImaging);

        Provider primeHospital = new Provider();
        primeHospital.setProviderName("Prime Hospital");
        primeHospital.setProviderSpeciality("Inpatient Facility");
        providerRepository.save(primeHospital);

        // 6️⃣ Claims
        Claim claim1 = buildClaim(member, riverClinic, "C-10421",
                LocalDate.of(2025, 8, 29), LocalDate.of(2025, 8, 29),
                LocalDate.of(2025, 9, 2), ClaimStatus.PROCESSED,
                300, 200, 155, 45);
        claimRepository.save(claim1);

        Claim claim2 = buildClaim(member, cityImaging, "C-10405",
                LocalDate.of(2025, 8, 20), LocalDate.of(2025, 8, 20),
                LocalDate.of(2025, 8, 22), ClaimStatus.DENIED,
                180, 0, 0, 0);
        claimRepository.save(claim2);

        Claim claim3 = buildClaim(member, primeHospital, "C-10398",
                LocalDate.of(2025, 8, 9), LocalDate.of(2025, 8, 9),
                LocalDate.of(2025, 8, 12), ClaimStatus.PAID,
                900, 700, 580, 120);
        claimRepository.save(claim3);

        Claim claim4 = buildClaim(member, riverClinic, "C-10375",
                LocalDate.of(2025, 7, 31), LocalDate.of(2025, 7, 31),
                LocalDate.of(2025, 8, 3), ClaimStatus.IN_REVIEW,
                250, 0, 0, 0);
        claimRepository.save(claim4);

        // 7️⃣ Claim Lines
        addClaimLines(claim1, List.of(
                new ClaimLineSpec("99213", "Office Visit, Est. Patient", 150, 100, 25, 10, 60, 15),
                new ClaimLineSpec("81002", "Urinalysis", 150, 100, 0, 0, 95, 5)
        ));

        addClaimLines(claim2, List.of(
                new ClaimLineSpec("70010", "Head MRI", 180, 0, 0, 0, 0, 0)
        ));

        addClaimLines(claim3, List.of(
                new ClaimLineSpec("99223", "Initial Hospital Care", 450, 350, 50, 20, 280, 50),
                new ClaimLineSpec("99238", "Discharge Day Management", 450, 350, 50, 0, 300, 50)
        ));

        addClaimLines(claim4, List.of(
                new ClaimLineSpec("87086", "Urine Culture", 250, 0, 0, 0, 0, 0)
        ));

        return member;
    }

    // 🧩 Helper to build claim
    private Claim buildClaim(Member member, Provider provider, String claimNumber,
                             LocalDate start, LocalDate end, LocalDate received,
                             ClaimStatus status, double billed, double allowed,
                             double planPaid, double memberResp) {

        Claim claim = new Claim();
        claim.setClaimNumber(claimNumber);
        claim.setMember(member);
        claim.setProvider(provider);
        claim.setServiceStartDate(start);
        claim.setServiceEndDate(end);
        claim.setReceivedDate(received);
        claim.setStatus(status);
        claim.setTotalBilled(BigDecimal.valueOf(billed));
        claim.setTotalAllowed(BigDecimal.valueOf(allowed));
        claim.setTotalPlanPaid(BigDecimal.valueOf(planPaid));
        claim.setTotalMemberResponsibility(BigDecimal.valueOf(memberResp));
        return claim;
    }

    // 🧩 Helper class to describe a claim line
    private record ClaimLineSpec(String cpt, String desc, double billed, double allowed,
                                 double copay, double coins, double planPaid, double memberResp) {
    }

    // 🧩 Helper to add lines
    private void addClaimLines(Claim claim, List<ClaimLineSpec> specs) {
        int lineNumber = 1;
        for (ClaimLineSpec s : specs) {
            ClaimLine line = new ClaimLine();
            line.setClaim(claim);
            line.setLineNumber(lineNumber++);
            line.setCptCode(s.cpt());
            line.setDescription(s.desc());
            line.setBilledAmount(BigDecimal.valueOf(s.billed()));
            line.setAllowedAmount(BigDecimal.valueOf(s.allowed()));
            line.setCopayApplied(BigDecimal.valueOf(s.copay()));
            line.setCoinsuranceApplied(BigDecimal.valueOf(s.coins()));
            line.setPlanPaid(BigDecimal.valueOf(s.planPaid()));
            line.setMemberResponsibility(BigDecimal.valueOf(s.memberResp()));
            claimLineRepository.save(line);
        }
    }
}
