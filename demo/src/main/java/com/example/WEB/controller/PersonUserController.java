package com.example.WEB.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.WEB.models.Gender;
import com.example.WEB.models.Person;
import com.example.WEB.models.User;
import com.example.WEB.repos.PersonRepo;
import com.example.WEB.repos.UserRepo;

@Controller
public class PersonUserController {
	
	@Autowired
	PersonRepo personRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@GetMapping("/test")
	public String test(User user, Model model) {
		Person adminPerson = new Person("Davis", "Blumbergs",24,Gender.Male,"Latvia","davisblumb@gmail.com");
		Person userPerson = new Person("Raitis", "Brasmanis",25,Gender.Male,"Latvia", "maxcape@inbox.lv");
		personRepo.save(adminPerson);
		personRepo.save(userPerson);
		
		User admin = new User("admininins", "administrator", adminPerson, true);
		User users = new User("useritis","userstrator", userPerson);
		userRepo.save(admin);
		userRepo.save(users);
		
		return "test";
	}
	
	@GetMapping("/register")
	public String register(User user, Model model) {
		model.addAttribute("types", Gender.values());
		
		return "register-login/register";
	}
	
	@PostMapping("/register")
	public String processRegister(@Valid User user, Model model, BindingResult res) throws IOException {
		System.out.println("Hello " + user.getUsername());
		
		if(userRepo.findByUsername(user.getUsername()) != null) {
			model.addAttribute("types", Gender.values());
			res.addError(new FieldError("user", "username", "this username already exists"));
			
			return "register-login/register";
		}
		
		if(personRepo.existsByEmail(user.getPerson().getEmail())) {
			model.addAttribute("types", Gender.values());
			res.addError(new FieldError("user", "person.email", "this e-mail is already taken"));
			
			return "register-login/register";
		}
		
        if(user.getPerson().getEmail().contains(",")) {
            model.addAttribute("types", Gender.values());
            res.addError(new FieldError("user", "person.email", "Invalid E-Mail address."));
            return "register-login/register";
        }
        

        if (res.hasErrors()){
            model.addAttribute("types", Gender.values());
            return "register-login/register";
        }
        
        Person tempPerson = new Person(user.getPerson().getName(),
        							   user.getPerson().getSurname(),
        							   user.getPerson().getAge(),
        							   user.getPerson().getGender(),
        							   user.getPerson().getCountry(),
        							   user.getPerson().getEmail());
        
        User tempUser = new User(user.getUsername(),
        						 user.getPassword(),
        						 tempPerson);
        
        personRepo.save(tempPerson);
        userRepo.save(tempUser);
        
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(User user) {
		return "register-login/login";
	}
	
	@PostMapping("/login")
	public String processLogin(@Valid User user, BindingResult res) {
		if(res.hasErrors()) {
			return "register-login/login";
		}
		
		User tempUser = userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if(tempUser == null) {
			res.addError(new FieldError("user", "username", "invalid username or password"));
			res.addError(new FieldError("user", "password", "invalid username or password"));
			
			return "register-login/login";
		}
		
		return "redirect:/test";
	}
}
