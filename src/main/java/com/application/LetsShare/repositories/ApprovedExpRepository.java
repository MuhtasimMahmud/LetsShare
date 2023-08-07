package com.application.LetsShare.repositories;

import com.application.LetsShare.models.ApprovedExperiences;
import com.application.LetsShare.models.RequestedExperiences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApprovedExpRepository extends JpaRepository<ApprovedExperiences, Integer> {

    @Query ("select A from ApprovedExperiences A where A.jobType = :type")
    public List<ApprovedExperiences> findAll(String type);

    public ApprovedExperiences findById(int id);

    public int findAllByjobType(String type);

    @Query ("select A from ApprovedExperiences A where A.postedBy = :email")
    public List<ApprovedExperiences> findAllByEmail(String email);


}
