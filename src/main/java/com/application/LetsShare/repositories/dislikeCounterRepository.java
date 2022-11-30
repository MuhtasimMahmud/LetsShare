package com.application.LetsShare.repositories;

import com.application.LetsShare.models.dislikeCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface dislikeCounterRepository extends JpaRepository <dislikeCounter, Integer> {

}
