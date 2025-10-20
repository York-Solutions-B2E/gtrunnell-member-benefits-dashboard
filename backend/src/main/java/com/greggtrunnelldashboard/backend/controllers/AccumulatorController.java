package com.greggtrunnelldashboard.backend.controllers;


import com.greggtrunnelldashboard.backend.entities.Accumulator;
import com.greggtrunnelldashboard.backend.services.AccumulatorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accumulators")
public class AccumulatorController {

    private final AccumulatorService accumulatorService;

    public AccumulatorController(AccumulatorService service) {
        this.accumulatorService = service;
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<Accumulator>> getByEnrollment(@PathVariable UUID enrollmentId) {
        return ResponseEntity.ok(accumulatorService.getByEnrollment(enrollmentId));
    }
    @GetMapping
    public ResponseEntity<List<Accumulator>> getAll() {
        return ResponseEntity.ok(accumulatorService.getAll());
    }

    @PostMapping
    public ResponseEntity<Accumulator> create(@RequestBody Accumulator acc) {
        return ResponseEntity.ok(accumulatorService.save(acc));
    }
}
