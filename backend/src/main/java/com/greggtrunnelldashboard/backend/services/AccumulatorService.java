package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.entities.Accumulator;
import com.greggtrunnelldashboard.backend.entities.Enrollment;
import com.greggtrunnelldashboard.backend.repositories.AccumulatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccumulatorService {

    private final AccumulatorRepository accumulatorRepository;

    public List<Accumulator> getByEnrollment(UUID enrollmentId){
        return accumulatorRepository.findByEnrollmentId(enrollmentId);
    }

    public Accumulator save(Accumulator accumulator){
        return accumulatorRepository.save(accumulator);
    }
    public List<Accumulator> getAll() {
        return accumulatorRepository.findAll();
    }
}
