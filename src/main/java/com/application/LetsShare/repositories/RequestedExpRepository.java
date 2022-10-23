package com.application.LetsShare.repositories;

import com.application.LetsShare.models.RequestedExperiences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestedExpRepository extends JpaRepository<RequestedExperiences, Integer> {

    public RequestedExperiences findById(int id);
}
