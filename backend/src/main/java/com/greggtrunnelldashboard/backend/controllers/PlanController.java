package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.entities.Plan;
import com.greggtrunnelldashboard.backend.services.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "http://localhost:5173")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }
    @GetMapping
    public List<Plan> findAll() {
        return planService.getAllPlans();
    }
    @PostMapping
    public Plan create(@RequestBody Plan plan) {
        return planService.createPlan(plan);
    }
}
