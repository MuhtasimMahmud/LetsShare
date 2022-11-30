package com.application.LetsShare.models;

import org.hibernate.annotations.Type;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDate;

@Entity
public class ApprovedExperiences{

    @Id
    private int id;
    private String candidateNickname;
    private String position;
    private String officeName;
    private String experience = "";
    private LocalDate postingDate;
    private String jobType; // software/HR/Marketing
    private String experienceSummary = "";
    private String postedBy;
    private String image;
    private int totalLike;
    private int totalDislike;


    public ApprovedExperiences(){

    }

    public ApprovedExperiences(int id, String candidateNickname, String position, String officeName, String experience, LocalDate postingDate, String jobType, String experienceSummary, String postedBy, String image, int totalLike, int totalDislike) {
        this.id = id;
        this.candidateNickname = candidateNickname;
        this.position = position;
        this.officeName = officeName;
        this.experience = experience;
        this.postingDate = postingDate;
        this.jobType = jobType;
        this.experienceSummary = experienceSummary;
        this.postedBy = postedBy;
        this.image = image;
        this.totalLike = totalLike;
        this.totalDislike = totalDislike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCandidateNickname() {
        return candidateNickname;
    }

    public void setCandidateNickname(String candidateNickname) {
        this.candidateNickname = candidateNickname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getExperienceSummary() {
        return experienceSummary;
    }

    public void setExperienceSummary(String experienceSummary) {
        this.experienceSummary = experienceSummary;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getTotalDislike() {
        return totalDislike;
    }

    public void setTotalDislike(int totalDislike) {
        this.totalDislike = totalDislike;
    }
}
