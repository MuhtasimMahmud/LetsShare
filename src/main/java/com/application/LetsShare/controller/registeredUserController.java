package com.application.LetsShare.controller;


import com.application.LetsShare.helper.Message;
import com.application.LetsShare.models.ApprovedExperiences;
import com.application.LetsShare.models.RequestedExperiences;
import com.application.LetsShare.models.User;
import com.application.LetsShare.models.likeCounter;
import com.application.LetsShare.repositories.ApprovedExpRepository;
import com.application.LetsShare.repositories.RequestedExpRepository;
import com.application.LetsShare.repositories.UserRepository;
import com.application.LetsShare.repositories.likeCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("registeredUser")
public class registeredUserController {


    @Autowired
    private RequestedExpRepository requestedExpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApprovedExpRepository approvedExpRepository;

    @Autowired
    likeCounterRepository likeCounterRepository;



    @RequestMapping("myProfile")
    public String myProfile(Model model, Principal principal){

        String userName = principal.getName();
        User user = userRepository.findByEmail(userName);

        model.addAttribute("user", user);

        List<ApprovedExperiences> experiences = approvedExpRepository.findAllByEmail(user.getEmail());
        model.addAttribute("experiences", experiences);

        return "RegisteredUsers/registeredUserProfile";
    }


    @RequestMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("user")User updatedUser, HttpSession session){

        User existingUser = userRepository.findByEmail(updatedUser.getEmail());

        try{
            if(existingUser != null){
                existingUser.setName(updatedUser.getName());
                existingUser.setOfficeName(updatedUser.getOfficeName());
                existingUser.setPosition(updatedUser.getPosition());

                userRepository.save(existingUser);
                session.setAttribute("message", new Message("Your Profile is Updated", "alert-success"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/registeredUser/myProfile";
    }


    @RequestMapping("SoftwareExperiencePage")
    public String software(Model model){

        List<ApprovedExperiences> experiences = approvedExpRepository.findAll("Software");
        model.addAttribute("experiences", experiences);
        model.addAttribute("expCount", experiences.size());

        return "RegisteredUsers/SoftwareExperiencePage";
    }

    @RequestMapping("MarketingExperiencePage")
    public String marketing(Model model){

        List<ApprovedExperiences> experiences = approvedExpRepository.findAll("Marketing");
        model.addAttribute("experiences", experiences);
        model.addAttribute("expCount", experiences.size());
        return "RegisteredUsers/MarketingExperiencePage";
    }

    @RequestMapping("HR_ExperiencePage")
    public String HR(Model model){

        List<ApprovedExperiences> experiences = approvedExpRepository.findAll("HR");
        model.addAttribute("experiences", experiences);
        model.addAttribute("expCount", experiences.size());
        return "RegisteredUsers/HR_ExperiencePage";
    }


    @RequestMapping("writeNewExperience")
    public String newExperience(Model model){

        model.addAttribute("experienceObj", new RequestedExperiences());
        return "RegisteredUsers/writeNewExperience";
    }


    @PostMapping("postExperience")
    public String postExperience(@ModelAttribute("experienceObj") RequestedExperiences requestedExperiences, Model model, HttpSession session, Principal principal){

        try{

            requestedExperiences.setPostingDate(LocalDate.now());
            requestedExperiences.setPostedBy(principal.getName());
            requestedExperiences.setTotalLike(0);
            requestedExperiences.setTotalDislike(0);
            RequestedExperiences result = requestedExpRepository.save(requestedExperiences);


            model.addAttribute("experienceObj", new RequestedExperiences());
            session.setAttribute("message", new Message("Your experience is submitted. Wait for the approval.", "alert-success"));

            return "RegisteredUsers/writeNewExperience";

        }catch (Exception exception){

            exception.printStackTrace();
            session.setAttribute("message", new Message("There is some problem happened. Please try again with correct info.", "alert-danger"));
            model.addAttribute("experienceObj", requestedExperiences);

            return "RegisteredUsers/writeNewExperience";
        }
    }


    @RequestMapping("/fullExperience/{id}")
    public String fullView(@PathVariable("id") int id, Model model){

        ApprovedExperiences experience = approvedExpRepository.findById(id);
        User user = userRepository.findByEmail(experience.getPostedBy());

        model.addAttribute("experience", experience);
        model.addAttribute("postingPerson", user);

        return "RegisteredUsers/fullExperience";
    }


    @RequestMapping("/editExperience/{id}")
    public String editExperience(@PathVariable("id") int id, Model model){

        ApprovedExperiences experience = approvedExpRepository.findById(id);
        User user = userRepository.findByEmail(experience.getPostedBy());

        model.addAttribute("experienceObj", experience);
        model.addAttribute("postingPerson", user);

        return "RegisteredUsers/editableExperience";
    }


    @RequestMapping(value = "/updateExperience", method = RequestMethod.POST)
    public String updateExperience(@ModelAttribute("experienceObj") ApprovedExperiences updatedApprovedExperiences, HttpSession session){

        ApprovedExperiences existingApprovedExperiences = approvedExpRepository.findById(updatedApprovedExperiences.getId());
        String url = "";

        try{
            if(existingApprovedExperiences != null){

                existingApprovedExperiences.setExperience(updatedApprovedExperiences.getExperience());
                existingApprovedExperiences.setExperienceSummary(updatedApprovedExperiences.getExperienceSummary());
                existingApprovedExperiences.setImage(updatedApprovedExperiences.getImage());
                approvedExpRepository.save(existingApprovedExperiences);

                session.setAttribute("message", new Message("Your experience is updated. You can check by clicking view button besides updated button.", "alert-success"));
                url = "redirect:/registeredUser/editExperience/"+existingApprovedExperiences.getId();
            }

        }catch (Exception exception){
            exception.printStackTrace();
            session.setAttribute("message", new Message("Sorry, your experience is not updated.", "alert-success"));
            url = "redirect:/registeredUser/editExperience/"+existingApprovedExperiences.getId();
        }

        return url;
    }




    @RequestMapping("myLikedExperiences")
    public String myLikedExperiences(Model model, Principal principal){

        List<likeCounter> likedExperiencesId = likeCounterRepository.findAllByLikedBy(principal.getName());
        List<ApprovedExperiences> LikedExperiencesList = new ArrayList<>();


        for(int i=0; i<likedExperiencesId.size(); i++){

            ApprovedExperiences exp = approvedExpRepository.findById(likedExperiencesId.get(i).getExperienceId());
            LikedExperiencesList.add(exp);

        }

        model.addAttribute("experiences", LikedExperiencesList);

        return "RegisteredUsers/myLikedExperiences";
    }






}


