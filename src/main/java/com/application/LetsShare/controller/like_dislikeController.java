package com.application.LetsShare.controller;

import com.application.LetsShare.models.ApprovedExperiences;
import com.application.LetsShare.models.dislikeCounter;
import com.application.LetsShare.models.likeCounter;
import com.application.LetsShare.repositories.ApprovedExpRepository;
import com.application.LetsShare.repositories.dislikeCounterRepository;
import com.application.LetsShare.repositories.likeCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class like_dislikeController {


    @Autowired
    ApprovedExpRepository approvedExpRepository;

    @Autowired
    likeCounterRepository likeCounterRepository;

    @Autowired
    dislikeCounterRepository dislikeCounterRepository;


    // RegisteredUser : like software experiences

    @RequestMapping("/registeredUser/likePost/{id}")
    public String likeSoftwareExperience(@PathVariable("id") int id, Model model, Principal principal){


        // if the current user not liked this post previously, only then we are giving access to like the post.

        likeCounter likedPosts = likeCounterRepository.findByExperienceId(id, principal.getName());

        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
        String jobType = approvedExperience.getJobType();


        if(likedPosts == null){
            approvedExperience.setTotalLike(approvedExperience.getTotalLike()+1);

            approvedExpRepository.save(approvedExperience);

            likeCounter likePost = new likeCounter();
            likePost.setLikedBy(principal.getName());
            likePost.setExperienceId(id);

            likeCounterRepository.save(likePost);


            // if this used already disliked this post previously, then here removing the dislike as this user is giving like now.

            dislikeCounter dislikedPosts = dislikeCounterRepository.findByExperienceId(id, principal.getName());

            if(dislikedPosts != null){

                approvedExperience.setTotalDislike(approvedExperience.getTotalDislike() - 1);
                approvedExpRepository.save(approvedExperience);

                dislikeCounterRepository.delete(dislikedPosts);
            }

        }else{

            switch (jobType) {
                case "Software":
                    return "redirect:/registeredUser/SoftwareExperiencePage";
                case "HR":
                    return "redirect:/registeredUser/HR_ExperiencePage";
                case "Marketing":
                    return "redirect:/registeredUser/MarketingExperiencePage";
            }

        }

        return switch (jobType) {
            case "Software" -> "redirect:/registeredUser/SoftwareExperiencePage";
            case "HR" -> "redirect:/registeredUser/HR_ExperiencePage";
            case "Marketing" -> "redirect:/registeredUser/MarketingExperiencePage";
            default -> "";
        };

        // eta kokhonoi run hobena ar ki cz uporei return chole jabe
    }



    // RegisterUser : dislike software experience

    @RequestMapping("/registeredUser/dislikePost/{id}")
    public String dislikeSoftwareExperience(@PathVariable("id") int id, Model model, Principal principal){

        // if the current user not disliked this post previously, only then we are giving access to dislike this post.

        dislikeCounter dislikedPosts = dislikeCounterRepository.findByExperienceId(id, principal.getName());

        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
        String jobType = approvedExperience.getJobType();

        if(dislikedPosts == null){

            approvedExperience.setTotalDislike(approvedExperience.getTotalDislike()+1);

            approvedExpRepository.save(approvedExperience);

            dislikeCounter dislikePost = new dislikeCounter();
            dislikePost.setDislikedBy(principal.getName());
            dislikePost.setExperienceId(id);

            dislikeCounterRepository.save(dislikePost);


            // if this current user already liked this post previously, then here we are removing that like as now the user is giving dislike.

            likeCounter likedPosts = likeCounterRepository.findByExperienceId(id, principal.getName());

            if(likedPosts != null) {
                approvedExperience.setTotalLike(approvedExperience.getTotalLike() - 1);

                approvedExpRepository.save(approvedExperience);

                likeCounterRepository.delete(likedPosts);
            }

        }else{

            switch (jobType) {
                case "Software":
                    return "redirect:/registeredUser/SoftwareExperiencePage";
                case "HR":
                    return "redirect:/registeredUser/HR_ExperiencePage";
                case "Marketing":
                    return "redirect:/registeredUser/MarketingExperiencePage";
            }

        }

        return switch (jobType) {
            case "Software" -> "redirect:/registeredUser/SoftwareExperiencePage";
            case "HR" -> "redirect:/registeredUser/HR_ExperiencePage";
            case "Marketing" -> "redirect:/registeredUser/MarketingExperiencePage";
            default -> "";
        };

    }






}
