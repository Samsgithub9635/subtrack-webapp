package com.subtrack.subtrack.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SubscriptionController {

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

}
