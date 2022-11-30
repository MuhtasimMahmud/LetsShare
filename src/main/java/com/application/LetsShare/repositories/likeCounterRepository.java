package com.application.LetsShare.repositories;

import com.application.LetsShare.models.likeCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface likeCounterRepository extends JpaRepository<likeCounter, Integer> {


    List<likeCounter> findAllByLikedBy(String email);

    @Query("select l from likeCounter l where l.experienceId = :id AND l.likedBy = :likedPersonEmail")
    public likeCounter findByExperienceId(int id, String likedPersonEmail);


}
