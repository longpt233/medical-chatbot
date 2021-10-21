package com.teamwork.springsecuritylogin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @GetMapping("/home")
    public String home(){
        return "This is home page";
    }
    @GetMapping("/admin")
    public String admin(){
        return "This is admin page";
    }
}
