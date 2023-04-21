package com.Estateapp.estate.Controller;

import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Entity.Visitors;
import com.Estateapp.estate.Helpers.EmailSenderService;
import com.Estateapp.estate.Helpers.Token;
import com.Estateapp.estate.Repository.UsersRepository;
import com.Estateapp.estate.Repository.VisitorsRepository;
import com.Estateapp.estate.Service.VisitorsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javax.imageio.ImageIO;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.threeten.bp.LocalDate;

import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorsService visitorsService;


    @Autowired
    private VisitorsRepository visitorsRepository;


    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/log")
    public ResponseEntity<Map<String, Object>> logVisitor(@RequestParam("name") String visitorName, @RequestParam("email") String email,
                                             @RequestParam("phone") String visitorPhone, @RequestParam("location") String location,
                                             @RequestParam("password") String password, @RequestParam("days") int days,
                                             @RequestParam("resident") String whomToSee, @RequestParam("dateOfVisit") String dateOfVisit, HttpSession session,
                                             Visitors visitors) throws ParseException, WriterException, IOException {

        Map<String, Object> responseBody = new HashMap<>();


        Users user = (Users) session.getAttribute("userprofile");
      System.out.println(user.getPassword());
      if(!BCrypt.checkpw(password, user.getPassword())){

          responseBody.put("message", "Wrong password");
          return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
//          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
      }

      else {

          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date date = null;
          date = sdf.parse(dateOfVisit);
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.DAY_OF_MONTH, days);
          date = calendar.getTime();

          String expectedDepartureDate = sdf.format(date);

          String token = visitorName.replace(" ", "_")+Token.generateToken();

          visitors.setDateOfVisit(sdf.format(sdf.parse(dateOfVisit)));
          visitors.setVisitor_code(token);
          visitors.setVisitor_name(visitorName);
          visitors.setVisitor_duration(days);
          visitors.setLocation(user.getHouse_address());
          visitors.setVisitor_email(email);
          visitors.setVisitor_phone(visitorPhone);
          visitors.setWhomToSee(whomToSee);
          visitors.setEntry_status("Pending");
          visitors.setExpectedDepartureDate(String.valueOf(expectedDepartureDate));

          visitorsService.saveNewVisitor(visitors);

          String text = "User Logged Successfully. Visitor's code is " + token + " and expected departure date is " + expectedDepartureDate;
          String data = "Visitor's name: "+visitors.getVisitor_name()+"\n Visitor's Code: "+visitors.getVisitor_code()+"\n Visitor's Phone number "+visitors.getVisitor_phone()+"\n Resident Name: "+visitors.getWhomToSee()
                  +"\n Resident Address: "+visitors.getLocation()+"\n Date of Visit: "+visitors.getDateOfVisit()+ "\n Duration of stay: "+visitors.getVisitor_duration();

          int width = 300;
          int height = 300;
          QRCodeWriter qrCodeWriter = new QRCodeWriter();
          ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
          BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
          MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
          byte[] imageBytes = pngOutputStream.toByteArray();

          // create response entity
          responseBody.put("message", "User Logged Successfully. Visitor's code is " + token + " and expected departure date is " + expectedDepartureDate + " Or scan below QR Code");
          responseBody.put("qrCodeImage", imageBytes);


          try {


              String htmlContent = "<html><body>" +
                      "<p>Hi " + visitors.getVisitor_name() + ",</p>" +
                      "<p>You have been logged in successfully. Please present this code to the security: " + visitors.getVisitor_code() + ".</p>" +
                      "<p>You can also present the QR code below to the security for fast validation before entry:</p>" +
                      "<img src='cid:qrCodeImage'>" +
                      "</body></html>";

              ByteArrayDataSource qrCodeImage = new ByteArrayDataSource(imageBytes, "image/png");

              emailSenderService.sendEmail(visitors.getVisitor_email(), "Visitor Log Notification For New Haven Estate", htmlContent, imageBytes );
          }
          catch (Exception e) {
              System.out.println(e.getMessage());
          }
          
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);

          return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);


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


    @PostMapping("/searchvisitor")
    public ResponseEntity<Visitors> searchVisitor(@RequestParam("code") String visitorCode) {
        Visitors visitors =  visitorsService.findId(visitorCode);

        return new ResponseEntity<>(visitors, HttpStatus.OK);
    }


//    @PostMapping("/searchvisitor")
//    public ResponseEntity<String> searchVisitors(@RequestParam("code") String visitorCode) throws JsonProcessingException, JsonProcessingException {
//        Visitors visitors = visitorsService.findId(visitorCode);
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(visitors);
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
//    }



    @GetMapping("/{id}/approve")
    public String changeStatus(@PathVariable("id") long id, Visitors visitors, RedirectAttributes redirectAttributes){

        return approveVisitor(id, visitors, redirectAttributes);

    }


    @GetMapping("/{id}/decline")
    public String denyVisitor(@PathVariable("id") long id, Visitors visitors, RedirectAttributes redirectAttributes){

        return declineVisitors(id, visitors, redirectAttributes);
    }


    @PutMapping("/updatestatus")
    @Modifying
    @Transactional
    public String approveVisitor(long id, Visitors visitors, RedirectAttributes redirectAttributes) {

        Visitors result = visitorsRepository.findByVisitorId(id);

        if(result.getEntry_status().equals("Pending")){
            result.setEntry_status("Approved");
            redirectAttributes.addFlashAttribute("record", "Great! The Visitor Can Go In!");
        }

        else if(result.getEntry_status().equals("Declined")) {
            result.setEntry_status("Approved");
            redirectAttributes.addFlashAttribute("record", "Good Job! Visitor has been reviewed and can go in now");
        }
        visitorsRepository.save(result);
        return "redirect:/web/security";
    }



    @PutMapping("/declinevisitor")
    @Modifying
    @Transactional
    public String declineVisitors(long id, Visitors visitors, RedirectAttributes redirectAttributes) {

        Visitors result = visitorsRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Visitor ID: " + id + " not found"));;

        if(result.getEntry_status().equals("Pending")){
            result.setEntry_status("Declined");
            redirectAttributes.addFlashAttribute("record", "Visitor denied Access");
        }

//        else if(result.getEntry_status().equals("Approved")) {
//            redirectAttributes.addFlashAttribute("record", "You can't modify a log that has been approved");
//        }

        visitorsRepository.save(result);

        return "redirect:/web/security";


    }



}
