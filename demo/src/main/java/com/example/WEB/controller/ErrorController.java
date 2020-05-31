package com.example.WEB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ErrorController {

    @GetMapping("/accessdenied")
    public String accessDenied(Principal principal, Model model){
        if(principal != null){
            model.addAttribute("msg",  principal.getName() + ", you are banned!");
        } else{
            model.addAttribute("msg", "Access denied!");
        }

        return "accessdenied";
    }
}
