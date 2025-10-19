package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.entities.Plan;
import com.greggtrunnelldashboard.backend.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }
    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

}
