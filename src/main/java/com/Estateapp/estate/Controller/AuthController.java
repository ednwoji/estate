package com.Estateapp.estate.Controller;

import com.Estateapp.estate.Entity.Roles;
import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Entity.Visitors;
import com.Estateapp.estate.Repository.UsersRepository;
import com.Estateapp.estate.Repository.VisitorsRepository;
import com.Estateapp.estate.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
public class AuthController {


    @Autowired
    private VisitorsRepository visitorsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;



    @GetMapping("/home")
    public String homePage() {
        return "Login";
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
        users.setRole(Roles.valueOf("USER"));


        try {
            userService.addNewUser(users);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("success", "Chief! You already have an account. Please sign in");
            return "redirect:/home";

        }

        if (!file.getContentType().equals("image/jpeg")) {
            log.info("Only JPEG format is allowed.");
            redirectAttributes.addFlashAttribute("success", "Only JPEG format is allowed.");

            return "redirect:/home";
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

    public void findVisitors() {

        Visitors visitors = visitorsRepository.findByVisitorCode("Ekene NwojiXjD7Fv");
//            int numberVisitors = visitors.size();
        System.out.println(visitors);


    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String email, @RequestParam("password") String userPassword, HttpSession session){

        Users user = usersRepository.findByEmail(email);

        if (user == null || !BCrypt.checkpw(userPassword,user.getPassword())) {
            // return an error response with status code 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } else {
                List<Visitors> visitors = visitorsRepository.findAllVisitorsForAUser(user.getName());
                int numberVisitors = visitors.size();

                String[] nameParts = user.getName().split(" ");
                String surname = nameParts[0];

                int family = usersRepository.checkFamilyMembers(surname, surname);

                System.out.println(family);


            session.setAttribute("user", user.getName());
            session.setAttribute("userRole", String.valueOf(user.getRole()));
            session.setAttribute("email", email);
            session.setAttribute("phone", user.getPhone_number());
            session.setAttribute("address", user.getHouse_address());
            session.setAttribute("userprofile", user);
            session.setAttribute("countvisitors", numberVisitors);
            session.setAttribute("countFamily", family);
            session.setAttribute("url", "/"+user.getName()+".jpg");
            // return a success response with status code 200 (OK)
            return ResponseEntity.ok("Login successful");
        }




    }

}
