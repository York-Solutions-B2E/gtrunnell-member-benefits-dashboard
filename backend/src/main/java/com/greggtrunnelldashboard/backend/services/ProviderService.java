package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.entities.Plan;
import com.greggtrunnelldashboard.backend.entities.Provider;
import com.greggtrunnelldashboard.backend.repositories.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }
    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }
}
