package com.application.LetsShare.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class dislikeCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int experienceId;
    private String dislikedBy;

    public dislikeCounter(){

    }

    public dislikeCounter(int id, int experienceId, String dislikedBy) {
        this.id = id;
        this.experienceId = experienceId;
        this.dislikedBy = dislikedBy;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(int experienceId) {
        this.experienceId = experienceId;
    }

    public String getDislikedBy() {
        return dislikedBy;
    }

    public void setDislikedBy(String dislikedBy) {
        this.dislikedBy = dislikedBy;
    }
}
