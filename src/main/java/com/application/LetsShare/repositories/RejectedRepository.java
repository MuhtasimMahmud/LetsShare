package com.application.LetsShare.repositories;


import com.application.LetsShare.models.ApprovedExperiences;
import com.application.LetsShare.models.RejectedExperiences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectedRepository extends JpaRepository<RejectedExperiences, Integer > {

    public RejectedExperiences findById(int id);

}
