package com.application.LetsShare.controller;


import com.application.LetsShare.models.ApprovedExperiences;
import com.application.LetsShare.repositories.ApprovedExpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class experienceCounterController {

    @Autowired
    private ApprovedExpRepository approvedExpRepository;


    @ResponseBody
    @GetMapping("SoftwareExperiencesCount")
    public int experiencesCount(Model model){
        List softwareExperienceCount = approvedExpRepository.findAll("Software");
        return softwareExperienceCount.size();
    }

}
