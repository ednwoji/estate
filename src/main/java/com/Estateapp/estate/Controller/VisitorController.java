package com.Estateapp.estate.Controller;

import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Entity.Visitors;
import com.Estateapp.estate.Helpers.Token;
import com.Estateapp.estate.Repository.UsersRepository;
import com.Estateapp.estate.Service.VisitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorsService visitorsService;

    @PostMapping("/log")
    public ResponseEntity<String> logVisitor(@RequestParam("name") String visitorName, @RequestParam("email") String email,
                                               @RequestParam("phone") String visitorPhone, @RequestParam("location") String location,
                                               @RequestParam("password") String password, @RequestParam("days") int days,
                                             @RequestParam("resident") String whomToSee, HttpSession session,
                                        Visitors visitors) {


      Users user = (Users) session.getAttribute("userprofile");
      System.out.println(user.getPassword());
      if(!BCrypt.checkpw(password, user.getPassword())){
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
      }

      else {

          String token = visitorName+Token.generateToken();

          visitors.setVisitor_code(token);
          visitors.setVisitor_name(visitorName);
          visitors.setVisitor_duration(days);
          visitors.setLocation(user.getHouse_address());
          visitors.setVisitor_email(email);
          visitors.setVisitor_phone(visitorPhone);
          visitors.setWhomToSee(whomToSee);

          visitorsService.saveNewVisitor(visitors);
          return ResponseEntity.ok("User Logged Successfully. Visitor's code is " +token);
      }
    }

    @PostMapping("/validateid")
    public ResponseEntity<?> validateVisitorID(@RequestParam("visitorId") String visitorId){
        Visitors visitors = visitorsService.findId(visitorId);
        if(visitors == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID Number");
        }
        else {
            return ResponseEntity.ok(visitors);
        }
    }
}
