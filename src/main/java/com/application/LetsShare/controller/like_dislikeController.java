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
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@ResponseBody
public class like_dislikeController {


    @Autowired
    ApprovedExpRepository approvedExpRepository;

    @Autowired
    likeCounterRepository likeCounterRepository;

    @Autowired
    dislikeCounterRepository dislikeCounterRepository;


    // RegisteredUser : like experiences

    @RequestMapping("/registeredUser/likePost/{id}")
    public int likeExperience(@PathVariable("id") int id, Model model, Principal principal){



        likeCounter likedPosts = likeCounterRepository.findByExperienceId(id, principal.getName());

        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
        String jobType = approvedExperience.getJobType();


        // if the current user not liked this post previously, only then we are giving access to like the post.

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

            // if this user already liked this post once then if he click like again then like will be removed.

            approvedExperience.setTotalLike(approvedExperience.getTotalLike() - 1);

            approvedExpRepository.save(approvedExperience);

            likeCounterRepository.delete(likedPosts);

        }

        return approvedExperience.getTotalLike();

    }



    // RegisterUser : dislike experience

    @RequestMapping("/registeredUser/dislikePost/{id}")
    public int dislikeExperience(@PathVariable("id") int id, Model model, Principal principal){

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

            // if this user already disliked this post then if he clicks it again then his dislike will be removed

            approvedExperience.setTotalDislike(approvedExperience.getTotalDislike() - 1);
            approvedExpRepository.save(approvedExperience);

            dislikeCounterRepository.delete(dislikedPosts);

        }

        return approvedExperience.getTotalDislike();

    }

    @RequestMapping("/getTotaLikes/{id}")
    public int checkTotalLikes(@PathVariable("id") int id){
        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
        int totalLikes = approvedExperience.getTotalLike();

        return totalLikes;
    }

    @RequestMapping("/getTotalDislikes/{id}")
    public int checkTotalDislikes(@PathVariable("id") int id){
        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
        int totalDislikes = approvedExperience.getTotalDislike();

        return totalDislikes;
    }

//
//
//    // Admin : like experiences
//
//    @RequestMapping("/admin/likePost/{id}")
//    public String adminlikeExperience(@PathVariable("id") int id, Model model, Principal principal){
//
//
//        // if the current user not liked this post previously, only then we are giving access to like the post.
//
//        likeCounter likedPosts = likeCounterRepository.findByExperienceId(id, principal.getName());
//
//        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
//        String jobType = approvedExperience.getJobType();
//
//
//        if(likedPosts == null){
//            approvedExperience.setTotalLike(approvedExperience.getTotalLike()+1);
//
//            approvedExpRepository.save(approvedExperience);
//
//            likeCounter likePost = new likeCounter();
//            likePost.setLikedBy(principal.getName());
//            likePost.setExperienceId(id);
//
//            likeCounterRepository.save(likePost);
//
//
//            // if this used already disliked this post previously, then here removing the dislike as this user is giving like now.
//
//            dislikeCounter dislikedPosts = dislikeCounterRepository.findByExperienceId(id, principal.getName());
//
//            if(dislikedPosts != null){
//
//                approvedExperience.setTotalDislike(approvedExperience.getTotalDislike() - 1);
//                approvedExpRepository.save(approvedExperience);
//
//                dislikeCounterRepository.delete(dislikedPosts);
//            }
//
//        }else{
//
//            approvedExperience.setTotalLike(approvedExperience.getTotalLike() - 1);
//
//            approvedExpRepository.save(approvedExperience);
//
//            likeCounterRepository.delete(likedPosts);
//
////            return "redirect:/admin/approvedExperiences";
//        }
//
//        return "redirect:/admin/approvedExperiences";
//    }
//
//
//    // Admin : dislike experience
//
//    @RequestMapping("/admin/dislikePost/{id}")
//    public String adminDislikeExperience(@PathVariable("id") int id, Model model, Principal principal){
//
//        // if the current user not disliked this post previously, only then we are giving access to dislike this post.
//
//        dislikeCounter dislikedPosts = dislikeCounterRepository.findByExperienceId(id, principal.getName());
//
//        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
//        String jobType = approvedExperience.getJobType();
//
//        if(dislikedPosts == null){
//
//            approvedExperience.setTotalDislike(approvedExperience.getTotalDislike()+1);
//
//            approvedExpRepository.save(approvedExperience);
//
//            dislikeCounter dislikePost = new dislikeCounter();
//            dislikePost.setDislikedBy(principal.getName());
//            dislikePost.setExperienceId(id);
//
//            dislikeCounterRepository.save(dislikePost);
//
//            // if this current user already liked this post previously, then here we are removing that like as now the user is giving dislike.
//
//            likeCounter likedPosts = likeCounterRepository.findByExperienceId(id, principal.getName());
//
//            if(likedPosts != null) {
//                approvedExperience.setTotalLike(approvedExperience.getTotalLike() - 1);
//
//                approvedExpRepository.save(approvedExperience);
//
//                likeCounterRepository.delete(likedPosts);
//            }
//
//        }else{
//
//            approvedExperience.setTotalDislike(approvedExperience.getTotalDislike() - 1);
//            approvedExpRepository.save(approvedExperience);
//
//            dislikeCounterRepository.delete(dislikedPosts);
//
////            return "redirect:/admin/approvedExperiences";
//        }
//
//        return "redirect:/admin/approvedExperiences";
//    }


}
