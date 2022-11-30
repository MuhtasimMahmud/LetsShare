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

    // jokhon dislike dibe tokhon like_counter thekeu dlt korte hobe
    // ar jokhon like dibe tokhon dislike counter thekeu dlt korte hobe





    // RegisteredUser : like software experiences

    @RequestMapping("/registeredUser/softwareExp/likePost/{id}")
    public String likeSoftwareExperience(@PathVariable("id") int id, Model model, Principal principal){


        // if the current user not liked this post previously, only then we are giving access to like the post.

        likeCounter likedPosts = likeCounterRepository.findByExperienceId(id, principal.getName());

        if(likedPosts == null){
            ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
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

//                dislikeCounter dislikePost = new dislikeCounter();
//                dislikePost.setDislikedBy(principal.getName());
//                dislikePost.setExperienceId(id);

                dislikeCounterRepository.delete(dislikedPosts);
            }

        }else{
            return "redirect:/registeredUser/SoftwareExperiencePage";
        }

        return "redirect:/registeredUser/SoftwareExperiencePage";
    }


    // RegisterUser : dislike software experience

    @RequestMapping("/registeredUser/softwareExp/dislikePost/{id}")
    public String dislikeSoftwareExperience(@PathVariable("id") int id, Model model, Principal principal){

        // if the current user not disliked this post previously, only then we are giving access to dislike this post.

        dislikeCounter dislikedPosts = dislikeCounterRepository.findByExperienceId(id, principal.getName());

        if(dislikedPosts == null){

            ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
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

//                likeCounter likePost = new likeCounter();
//                likePost.setLikedBy(principal.getName());
//                likePost.setExperienceId(id);

                likeCounterRepository.delete(likedPosts);
            }

        }else{
            return "redirect:/registeredUser/SoftwareExperiencePage";
        }

        return "redirect:/registeredUser/SoftwareExperiencePage";
    }


}
