package com.Estateapp.estate.Controller;


import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Entity.Visitors;
import com.Estateapp.estate.Repository.UsersRepository;
import com.Estateapp.estate.Repository.VisitorsRepository;
import com.Estateapp.estate.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/web")
@Slf4j
public class WebController {
    @Autowired
    private VisitorsRepository visitorsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;


//    @GetMapping("/dashboard")
//    public String dashboardPage(HttpSession session, Model model) {
//
////        Users users = (Users) session.getAttribute("userprofile");
////
////        List<Visitors> visitors = visitorsRepository.findAllVisitorsForAUser(users.getName());
////        model.addAttribute("user", users);
//        return "Dashboard";
//    }

    @GetMapping("/dashboard")
    public String dashboardPage() {

//        Users users = (Users) session.getAttribute("userprofile");
//
//        List<Visitors> visitors = visitorsRepository.findAllVisitorsForAUser(users.getName());
//        model.addAttribute("user", users);
        return "Dashboard";
    }



    @GetMapping("/profile")
    public String profilePage() {

//        Users users = (Users) session.getAttribute("userprofile");
//
//        List<Visitors> visitors = visitorsRepository.findAllVisitorsForAUser(users.getName());
//        model.addAttribute("user", users);
        return "profile";
    }


    @GetMapping("/visitors")
    public String addVisitorPage() {
        return "AddVisitors";
    }


    @GetMapping("/view")
    public String viewVisitorsPage(HttpSession session, Model model) {

        List<Visitors> visitors = visitorsRepository.findAll();
        List<Visitors> unapprovedVisitors = visitorsRepository.findVisitorsByStatus("Pending");
        List<Visitors> approvedVisitors = visitorsRepository.findVisitorsByStatus("Approved");

        model.addAttribute("allVisitors", visitors);
        model.addAttribute("unapprovedVisitors", unapprovedVisitors);
        model.addAttribute("approvedVisitors", approvedVisitors);

//        session.setAttribute("allVisitors", visitors);
//        session.setAttribute("unapprovedVisitors", unapprovedVisitors);
//        session.setAttribute("approvedVisitors", approvedVisitors);

        return "viewvisitors";
    }




    @GetMapping("/sessionExpired")
    public RedirectView sessionExpired(HttpServletResponse response) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return new RedirectView("/web/logout", true, false, true);
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes)
    {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Logged out Successfully");
        return "redirect:/home";
    }


    @GetMapping("/validate")
    public String securityPage() {
        return "Security";
    }





}

