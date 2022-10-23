package com.application.LetsShare.controller;

import com.application.LetsShare.models.ApprovedExperiences;
import com.application.LetsShare.models.RequestedExperiences;
import com.application.LetsShare.models.User;
import com.application.LetsShare.repositories.ApprovedExpRepository;
import com.application.LetsShare.repositories.RequestedExpRepository;
import com.application.LetsShare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("allUsers")
public class allUsersController {

    @Autowired
    private ApprovedExpRepository approvedExpRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("SoftwareExperiencePage")
    public String software(Model model){

        List<ApprovedExperiences> experiences = approvedExpRepository.findAll("Software");
        model.addAttribute("experiences", experiences);
        model.addAttribute("expCount", experiences.size());
        return "allUsers/SoftwareExperiencePage";
    }




    @RequestMapping("MarketingExperiencePage")
    public String marketing(Model model){

        List<ApprovedExperiences> experiences = approvedExpRepository.findAll("Marketing");
        model.addAttribute("experiences", experiences);
        model.addAttribute("expCount", experiences.size());
        return "allUsers/MarketingExperiencePage";
    }


    @RequestMapping("HR_ExperiencePage")
    public String HR(Model model){

        List<ApprovedExperiences> experiences = approvedExpRepository.findAll("HR");
        model.addAttribute("experiences", experiences);
        model.addAttribute("expCount", experiences.size());
        return "allUsers/HR_ExperiencePage";
    }


    @RequestMapping("/fullExperience/{id}")
    public String fullView(@PathVariable("id") int id, Model model){

        ApprovedExperiences experience = approvedExpRepository.findById(id);
        User user = userRepository.findByEmail(experience.getPostedBy());

        model.addAttribute("experience", experience);
        model.addAttribute("postingPerson", user);

        return "allUsers/fullExperience";
    }


}
