package com.Estateapp.estate.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/web")
public class WebController {


    @GetMapping("/home")
    public String homePage(){

        return "index";
    }


    @PostMapping("/upload")
    public String addNewUser(@RequestParam("file") MultipartFile file) {

                if (!file.getContentType().equals("image/jpeg")) {
                    return "Only JPEG format is allowed.";
                }
                try {
                    byte[] bytes = file.getBytes();
                    String customFileName = "your_custom_file_name.jpg";
                    Path path = Paths.get("src/main/resources/uploaded-files/" + customFileName);
                    Files.write(path, bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "File uploaded successfully!";
            }



}
