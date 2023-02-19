package com.Estateapp.estate.Controller;


import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Repository.UsersRepository;
import com.Estateapp.estate.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/web")
@Slf4j
public class WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/home")
    public String homePage() {

        return "index";
    }


    @PostMapping("/upload")
    public String addNewUser(@RequestParam("file") MultipartFile file,
                             @RequestParam("fname") String name, @RequestParam("surname") String surname,
                             @RequestParam("phone") String phone, @RequestParam("logemail") String email,
                             @RequestParam("road") int roadNumber, @RequestParam("plot") int plotNumber,
                             @RequestParam("area") String area, @RequestParam("logpass") String userPassword, Users users,
                             RedirectAttributes redirectAttributes) {

        String houseAddress = area + " Road " + roadNumber + " Plot " + plotNumber;
        System.out.println(houseAddress);
        String hashed_password = BCrypt.hashpw(userPassword, BCrypt.gensalt());


        String fullName = surname + " " + name;
        users.setEmail(email);
        users.setName(fullName);
        users.setHouse_address(houseAddress);
        users.setPhone_number(phone);
        users.setPassword(hashed_password);


        try {
            userService.addNewUser(users);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("success", "User already exists on the DB");
            return "redirect:/web/home";

        }

        if (!file.getContentType().equals("image/jpeg")) {
            log.info("Only JPEG format is allowed.");
            redirectAttributes.addFlashAttribute("success", "Only JPEG format is allowed.");

            return "redirect:/web/home";
        }

        try {
            byte[] bytes = file.getBytes();
            String customFileName = fullName+".jpg";
            Path path = Paths.get("src/main/resources/uploaded-files/" + customFileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("File uploaded successfully!");

        redirectAttributes.addFlashAttribute("success", "User Added Successfully");

        return "redirect:/web/home";
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String email, @RequestParam("password") String userPassword, HttpSession session){

        Users user = usersRepository.findByEmail(email);
        if (user == null || !BCrypt.checkpw(userPassword,user.getPassword())) {
            // return an error response with status code 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } else {

            session.setAttribute("user", user.getName());
            session.setAttribute("address", user.getHouse_address());
            session.setAttribute("userprofile", user);
            session.setAttribute("url", "/"+user.getName()+".jpg");
            // return a success response with status code 200 (OK)
            return ResponseEntity.ok("Login successful");
        }




    }

    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session, Model model) {

        Users users = (Users) session.getAttribute("userprofile");
        model.addAttribute("user", users);
        return "Dashboard";
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
        return "redirect:/web/home";
    }


    @GetMapping("/validate")
    public String securityPage() {

        return "Security";

    }



}

