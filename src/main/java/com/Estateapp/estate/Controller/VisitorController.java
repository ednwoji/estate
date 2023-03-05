package com.Estateapp.estate.Controller;

import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Entity.Visitors;
import com.Estateapp.estate.Helpers.Token;
import com.Estateapp.estate.Repository.UsersRepository;
import com.Estateapp.estate.Service.VisitorsService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.threeten.bp.LocalDate;

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

          LocalDate currentDate = LocalDate.now();
          LocalDate expectedDepartureDate = currentDate.plusDays(3);
          System.out.println(visitorName.replace(" ", "_"));

          String token = visitorName.replace(" ", "_")+Token.generateToken();

          visitors.setVisitor_code(token);
          visitors.setVisitor_name(visitorName);
          visitors.setVisitor_duration(days);
          visitors.setLocation(user.getHouse_address());
          visitors.setVisitor_email(email);
          visitors.setVisitor_phone(visitorPhone);
          visitors.setWhomToSee(whomToSee);
          visitors.setEntry_status("Pending");
          visitors.setExpectedDepartureDate(expectedDepartureDate);

          visitorsService.saveNewVisitor(visitors);
          return ResponseEntity.ok("User Logged Successfully. Visitor's code is " +token+ " and expected departure date is " +expectedDepartureDate);
      }
    }

    @GetMapping("/validateid/{visitorId}")
    public ResponseEntity<?> validateVisitorID(@PathVariable("visitorId") String visitorId){
        Visitors visitors = visitorsService.findId(visitorId);
        if(visitors == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID Number");
        }
        else {
            return ResponseEntity.ok(visitors);
        }

    }

    @GetMapping("/check/{visitorId}")
    public ResponseEntity<?> approveOrDeclineVisitor(@PathVariable("visitorId") String visitorId){
        Visitors visitors = visitorsService.findId(visitorId);

        if(visitors == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Visitor ID");
        }

        else {

            visitors.setEntry_status("Approved");
            visitorsService.saveNewVisitor(visitors);
//        visitorsService.updateVisitor("Approved", visitorId);
            return ResponseEntity.ok("Updated Successfully");
        }
    }



}
