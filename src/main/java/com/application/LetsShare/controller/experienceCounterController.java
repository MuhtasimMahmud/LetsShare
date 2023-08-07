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
    public int softwareExperiencesCount(Model model){
        List ExperienceCount = approvedExpRepository.findAll("Software");
        return ExperienceCount.size();
    }


    @ResponseBody
    @GetMapping("HRExperiencesCount")
    public int HRExperiencesCount(Model model){
        List ExperienceCount = approvedExpRepository.findAll("HR");
        return ExperienceCount.size();
    }

    @ResponseBody
    @GetMapping("marketingExperiencesCount")
    public int marketingExperiencesCount(Model model){
        List ExperienceCount = approvedExpRepository.findAll("Marketing");
        return ExperienceCount.size();
    }




}
