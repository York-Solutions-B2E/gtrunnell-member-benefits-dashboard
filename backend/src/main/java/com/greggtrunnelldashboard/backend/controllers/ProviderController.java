package com.greggtrunnelldashboard.backend.controllers;


import com.greggtrunnelldashboard.backend.entities.Provider;
import com.greggtrunnelldashboard.backend.services.ProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@CrossOrigin(origins = "http://localhost:5173")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }
    @GetMapping
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }
    @PostMapping
    public Provider create(@RequestBody Provider provider) {
        return providerService.createProvider(provider);
    }
}
