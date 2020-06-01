package com.example.WEB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ErrorController {

    @GetMapping("/accessdenied")
    public String accessDenied(Principal principal, Model model){
    	model.addAttribute("principal", principal);
        if(principal != null){
            model.addAttribute("msg",  principal.getName() + ", you are not allowed!");
            model.addAttribute("principal", principal);
        } else{
            model.addAttribute("msg", "Access denied!");
            model.addAttribute("principal", principal);
        }

        return "accessdenied";
    }
}
