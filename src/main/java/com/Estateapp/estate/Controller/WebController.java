package com.Estateapp.estate.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {


    @GetMapping("/home")
    public String homePage(){

        return "index";
    }
}
