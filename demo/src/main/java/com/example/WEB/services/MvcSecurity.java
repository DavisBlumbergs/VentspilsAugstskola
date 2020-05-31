package com.example.WEB.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@ComponentScan("org.springframework.security.samples.mvc")
public class MvcSecurity implements WebMvcConfigurer {

    // ...

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/accessdenied").setViewName("test");	
    	registry.addViewController("/login").setViewName("login");
    }
}