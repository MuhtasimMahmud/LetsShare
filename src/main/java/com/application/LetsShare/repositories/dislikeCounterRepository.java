package com.application.LetsShare.repositories;

import com.application.LetsShare.models.dislikeCounter;
import com.application.LetsShare.models.likeCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface dislikeCounterRepository extends JpaRepository <dislikeCounter, Integer> {


    @Query("select d from dislikeCounter d where d.experienceId = :id AND d.dislikedBy = :dislikedPersonEmail")
    public dislikeCounter findByExperienceId(int id, String dislikedPersonEmail);

}
