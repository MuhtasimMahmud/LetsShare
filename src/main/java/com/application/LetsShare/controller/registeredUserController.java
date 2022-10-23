package com.application.LetsShare.controller;


import com.application.LetsShare.helper.Message;
import com.application.LetsShare.models.ApprovedExperiences;
import com.application.LetsShare.models.RequestedExperiences;
import com.application.LetsShare.models.User;
import com.application.LetsShare.repositories.ApprovedExpRepository;
import com.application.LetsShare.repositories.RequestedExpRepository;
import com.application.LetsShare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
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
        return "RegisteredUsers/registeredUserProfile";
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


    @RequestMapping("postExperience")
    public String postExperience(@ModelAttribute("experienceObj") RequestedExperiences requestedExperiences, Model model, HttpSession session, Principal principal){

        try{

            requestedExperiences.setPostingDate(LocalDate.now());
            requestedExperiences.setPostedBy(principal.getName());
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

}


