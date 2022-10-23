package com.application.LetsShare.controller;

import com.application.LetsShare.helper.Message;
import com.application.LetsShare.models.User;
import com.application.LetsShare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class signUpController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/signUp")
    public String signUP(Model model){
        model.addAttribute("title", "Register Account");
        model.addAttribute("user", new User());
        return "signUp";
    }


    //handler for registering user
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user, Model model, HttpSession session){


        try{

            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User UserResult = this.userRepository.save(user);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));

            return "signUp";


        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong !!" + e.getMessage(), "alert-danger"));

            return "signUp";
        }

    }








}
