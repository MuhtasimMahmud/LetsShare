package com.application.LetsShare.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class likeCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int experienceId;
    private int likedBy;

    public likeCounter() {

    }

    public likeCounter(int id, int experienceId, int likedBy) {
        this.id = id;
        this.experienceId = experienceId;
        this.likedBy = likedBy;
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

    public int getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(int likedBy) {
        this.likedBy = likedBy;
    }
}
