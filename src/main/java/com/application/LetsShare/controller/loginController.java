package com.application.LetsShare.controller;

import com.application.LetsShare.models.User;
import com.application.LetsShare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {


    @Autowired
    UserRepository userRepository;


    // handler for custom login
    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title", "Login");
        return "login";
    }

}
