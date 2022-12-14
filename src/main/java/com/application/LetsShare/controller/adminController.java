package com.application.LetsShare.controller;

import com.application.LetsShare.helper.Message;
import com.application.LetsShare.models.*;
import com.application.LetsShare.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminController {


    @Autowired
    private RequestedExpRepository requestedExpRepository;

    @Autowired
    private ApprovedExpRepository approvedExpRepository;

    @Autowired
    private RejectedRepository rejectedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    likeCounterRepository likeCounterRepository;

    @Autowired
    dislikeCounterRepository dislikeCounterRepository;


    @RequestMapping("adminDashboard")
    public String admin(Model model){

        model.addAttribute("approveCount", approvedExpRepository.count());
        model.addAttribute("requestCount", requestedExpRepository.count());
        model.addAttribute("rejectCount", rejectedRepository.count());

        return "admin/adminHome";
    }

    @RequestMapping("approvedExperiences")
    public String approvedExp(Model model){

        List<ApprovedExperiences> experiences = approvedExpRepository.findAll();
        model.addAttribute("experiences", experiences);

        return "admin/approvedExperiences";
    }

    @RequestMapping("requestedExperiences")
    public String requestExp(Model model){

        List<RequestedExperiences> experiences = requestedExpRepository.findAll();
        model.addAttribute("experiences", experiences);

        return "admin/requestedExperiences";
    }

    @RequestMapping("rejectedExperiences")
    public String rejectedExp(Model model){

        List<RejectedExperiences> experiences = rejectedRepository.findAll();
        model.addAttribute("experiences", experiences);

        return "admin/rejectedExperiences";
    }


    @RequestMapping("writeExperiences")
    public String postExp(Model model){

        model.addAttribute("experienceObj", new ApprovedExperiences());
        return "admin/writeExperience";
    }


    @RequestMapping("postExperience")
    public String postExperience(@ModelAttribute("experienceObj") RequestedExperiences requestedExperiences, Model model, HttpSession session, Principal principal){

        try{

            requestedExperiences.setPostingDate(LocalDate.now());
            requestedExperiences.setPostedBy(principal.getName());
            requestedExperiences.setTotalLike(0);
            requestedExperiences.setTotalDislike(0);
            RequestedExperiences result = requestedExpRepository.save(requestedExperiences);

            model.addAttribute("experienceObj", new RequestedExperiences());
            session.setAttribute("message", new Message("As you are admin, so please approve your own experience from requested experience list.", "alert-success"));


            return "admin/writeExperience";

        }catch (Exception exception){

            exception.printStackTrace();
            session.setAttribute("message", new Message("There is some problem happened. Please try again with correct info.", "alert-danger"));
            model.addAttribute("experienceObj", requestedExperiences);

            return "admin/writeExperience";
        }
    }



    @RequestMapping("myProfile")
    public String myProfile(Model model){

        User user = userRepository.findByRole("ROLE_ADMIN");
        model.addAttribute("user", user);


        List<ApprovedExperiences> experiences = approvedExpRepository.findAllByEmail(user.getEmail());
        model.addAttribute("experiences", experiences);

        return "admin/myProfile";
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
        return "redirect:/admin/myProfile";
    }


    @RequestMapping("/postApproved/{id}")
    public String approved(@PathVariable("id") int id){

        RequestedExperiences requestedExperience = requestedExpRepository.findById(id);

        ApprovedExperiences approvedExperience = new ApprovedExperiences();

        approvedExperience.setId(requestedExperience.getId());
        approvedExperience.setCandidateNickname(requestedExperience.getCandidateNickname());
        approvedExperience.setPosition(requestedExperience.getPosition());
        approvedExperience.setOfficeName(requestedExperience.getOfficeName());
        approvedExperience.setExperience(requestedExperience.getExperience());
        approvedExperience.setPostingDate(requestedExperience.getPostingDate());
        approvedExperience.setJobType(requestedExperience.getJobType());
        approvedExperience.setExperienceSummary(requestedExperience.getExperienceSummary());
        approvedExperience.setPostedBy(requestedExperience.getPostedBy());
        approvedExperience.setImage(requestedExperience.getImage());
        approvedExperience.setTotalLike(requestedExperience.getTotalLike());
        approvedExperience.setTotalDislike(requestedExperience.getTotalDislike());


        try{
            if(requestedExperience != null){
                requestedExpRepository.delete(requestedExperience);
                approvedExpRepository.save(approvedExperience);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/requestedExperiences";
    }


    @RequestMapping("/postRejected/{id}")
    public String rejected(@PathVariable("id") int id){

        RequestedExperiences requestedExperience = requestedExpRepository.findById(id);
        RejectedExperiences rejectedExperiences = new RejectedExperiences();

        rejectedExperiences.setId(requestedExperience.getId());
        rejectedExperiences.setCandidateNickname(requestedExperience.getCandidateNickname());
        rejectedExperiences.setPosition(requestedExperience.getPosition());
        rejectedExperiences.setOfficeName(requestedExperience.getOfficeName());
        rejectedExperiences.setExperience(requestedExperience.getExperience());
        rejectedExperiences.setPostingDate(requestedExperience.getPostingDate());
        rejectedExperiences.setJobType(requestedExperience.getJobType());
        rejectedExperiences.setExperienceSummary(requestedExperience.getExperienceSummary());
        rejectedExperiences.setPostedBy(requestedExperience.getPostedBy());
        rejectedExperiences.setImage(requestedExperience.getImage());
        rejectedExperiences.setTotalLike(requestedExperience.getTotalLike());
        rejectedExperiences.setTotalDislike(requestedExperience.getTotalDislike());

        try{
            if(requestedExperience != null){
                requestedExpRepository.delete(requestedExperience);
                rejectedRepository.save(rejectedExperiences);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/requestedExperiences";
    }



    @RequestMapping("/rejectPostFromApprovedList/{id}")
    public String rejectFromApproved(@PathVariable("id") int id){

        ApprovedExperiences approvedExperience = approvedExpRepository.findById(id);
        RejectedExperiences rejectedExperience = new RejectedExperiences();

        rejectedExperience.setId(approvedExperience.getId());
        rejectedExperience.setCandidateNickname(approvedExperience.getCandidateNickname());
        rejectedExperience.setPosition(approvedExperience.getPosition());
        rejectedExperience.setOfficeName(approvedExperience.getOfficeName());
        rejectedExperience.setExperience(approvedExperience.getExperience());
        rejectedExperience.setPostingDate(approvedExperience.getPostingDate());
        rejectedExperience.setJobType(approvedExperience.getJobType());
        rejectedExperience.setExperienceSummary(approvedExperience.getExperienceSummary());
        rejectedExperience.setPostedBy(approvedExperience.getPostedBy());
        rejectedExperience.setImage(approvedExperience.getImage());
        rejectedExperience.setTotalLike(approvedExperience.getTotalLike());
        rejectedExperience.setTotalDislike(approvedExperience.getTotalDislike());

        try{
            if(approvedExperience != null){
                
                approvedExpRepository.delete(approvedExperience);
                rejectedRepository.save(rejectedExperience);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/approvedExperiences";
    }



    @RequestMapping("approveFromRejectedList/{id}")
    public String approveFromRejected(@PathVariable("id") int id){

        RejectedExperiences rejectedExperience = rejectedRepository.findById(id);
        ApprovedExperiences approvedExperience = new ApprovedExperiences();

        approvedExperience.setId(rejectedExperience.getId());
        approvedExperience.setCandidateNickname(rejectedExperience.getCandidateNickname());
        approvedExperience.setPosition(rejectedExperience.getPosition());
        approvedExperience.setOfficeName(rejectedExperience.getOfficeName());
        approvedExperience.setExperience(rejectedExperience.getExperience());
        approvedExperience.setPostingDate(rejectedExperience.getPostingDate());
        approvedExperience.setJobType(rejectedExperience.getJobType());
        approvedExperience.setExperienceSummary(rejectedExperience.getExperienceSummary());
        approvedExperience.setPostedBy(rejectedExperience.getPostedBy());
        approvedExperience.setImage(rejectedExperience.getImage());
        approvedExperience.setTotalLike(rejectedExperience.getTotalLike());
        approvedExperience.setTotalDislike(rejectedExperience.getTotalDislike());

        try{
            if(approvedExperience != null){
                approvedExpRepository.save(approvedExperience);
                rejectedRepository.delete(rejectedExperience);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/rejectedExperiences";
    }


    @RequestMapping("/fullExperience/{id}")
    public String fullView(@PathVariable("id") int id, Model model){

        ApprovedExperiences experience = approvedExpRepository.findById(id);
        User user = userRepository.findByEmail(experience.getPostedBy());

        model.addAttribute("experience", experience);
        model.addAttribute("postingPerson", user);

        return "admin/fullExperience";
    }

    @RequestMapping("/fullExperienceRequestedExp/{id}")
    public String fullViewFromRequestedExp(@PathVariable("id") int id, Model model){

        RequestedExperiences experience = requestedExpRepository.findById(id);
        User user = userRepository.findByEmail(experience.getPostedBy());

        model.addAttribute("experience", experience);
        model.addAttribute("postingPerson", user);

        return "admin/fullExperience";
    }

    @RequestMapping("/fullExperienceRejectedExp/{id}")
    public String fullViewFromRejectedExp(@PathVariable("id") int id, Model model){

        RejectedExperiences experience = rejectedRepository.findById(id);
        User user = userRepository.findByEmail(experience.getPostedBy());

        model.addAttribute("experience", experience);
        model.addAttribute("postingPerson", user);

        return "admin/fullExperience";
    }


    @RequestMapping("/editExperience/{id}")
    public String editExperience(@PathVariable("id") int id, Model model){

        ApprovedExperiences experience = approvedExpRepository.findById(id);
        User user = userRepository.findByEmail(experience.getPostedBy());

        model.addAttribute("experienceObj", experience);
        model.addAttribute("postingPerson", user);

        return "admin/editableExperience";
    }


    @RequestMapping(value = "/updateExperience", method = RequestMethod.POST)
    public String updateExperience(@ModelAttribute("experienceObj") ApprovedExperiences updatedApprovedExperiences, HttpSession session){

        ApprovedExperiences existingApprovedExperiences = approvedExpRepository.findById(updatedApprovedExperiences.getId());
        String url = "";

        try{
            if(existingApprovedExperiences != null){

                existingApprovedExperiences.setExperience(updatedApprovedExperiences.getExperience());
                existingApprovedExperiences.setExperienceSummary(updatedApprovedExperiences.getExperienceSummary());
                approvedExpRepository.save(existingApprovedExperiences);

                session.setAttribute("message", new Message("Your experience is updated. You can check by clicking view button besides updated button.", "alert-success"));
                url = "redirect:/admin/editExperience/"+existingApprovedExperiences.getId();
            }

        }catch (Exception exception){
            exception.printStackTrace();
            session.setAttribute("message", new Message("Sorry, your experience is not updated.", "alert-success"));
            url = "redirect:/admin/editExperience/"+existingApprovedExperiences.getId();
        }

        return url;
    }


    @RequestMapping("seeWhoLiked/{id}")
    public String seeWhoLiked(@PathVariable("id") int id, Model model){

        List<likeCounter> likedPersonsList = likeCounterRepository.findAllByExperienceId(id);
        model.addAttribute("likedPersonsList", likedPersonsList);
        model.addAttribute("experienceID", id);

        return "admin/likedPersonsList";
    }


    @RequestMapping("seeWhoDisliked/{id}")
    public String seeWhoDisliked(@PathVariable("id") int id, Model model){

        List<dislikeCounter> dislikeCounterList = dislikeCounterRepository.findAllByExperienceId(id);
        model.addAttribute("dislikedPersonsList", dislikeCounterList);
        model.addAttribute("experienceID", id);

        return "admin/dislikedPersonsList";

    }



}
