package com.application.LetsShare.controller;

import com.application.LetsShare.models.ApprovedExperiences;
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

    @RequestMapping("/registeredUser/softwareExp/likePost/{id}")
    public String likeSoftwareExperience(@PathVariable("id") int id, Model model, Principal principal){


        likeCounter likedPosts = likeCounterRepository.findByExperienceId(id, principal.getName());

        if(likedPosts == null){
            ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
            approvedExperience.setTotalLike(approvedExperience.getTotalLike()+1);

            approvedExpRepository.save(approvedExperience);

            likeCounter likePost = new likeCounter();
            likePost.setLikedBy(principal.getName());
            likePost.setExperienceId(id);

            likeCounterRepository.save(likePost);
        }else{
            return "redirect:/registeredUser/SoftwareExperiencePage";
        }


        return "redirect:/registeredUser/SoftwareExperiencePage";
    }


}
