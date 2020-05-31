package com.example.WEB.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.WEB.models.Gender;
import com.example.WEB.models.Person;
import com.example.WEB.models.User;
import com.example.WEB.repos.PersonRepo;
import com.example.WEB.repos.ProductRepo;
import com.example.WEB.repos.UserRepo;
import com.example.WEB.services.PersonServImpl;
import com.example.WEB.services.UserServImpl;

@Controller
@RequestMapping(value = "/admin")
public class AdminPersonUserController {
	
	@Autowired
	PersonRepo personRepo;
	
	@Autowired
	PersonServImpl personService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserServImpl userService;
	
//###### Update User & Person ######
	//###### Load User data in view ######
	@GetMapping(value = "/selectUserUpdate")
	public String selectUserToUpdateGet(User user, Model model) {
		model.addAttribute("allUsers", userService.selectAll());
		
		return "admin/user/selectUserUpdate";
	}
	
	//###### Use User data from view ######
	@PostMapping(value = "/selectUserUpdate")
	public String selectUserToUpdatePost(User user, Model model) {
		return "redirect:/admin/updateUser/" + user.getU_id();
	}
	
	//###### Load User data to Update in view ######
	@GetMapping(value = "/updateUser/{id}")
	public String updateUserGet(@PathVariable(name = "id") int id, User user, Model model) {
		model.addAttribute("oldUser", userRepo.findById((long)id).get());
		
		return "admin/user/updateUser";
	}
	
	//###### Use user data to Update from view ######
	@PostMapping(value = "/updateUser/{id}")
	public String updateUserPost(@PathVariable(name = "id")int id, @Valid User user, BindingResult res, Model model) {
		
		if(res.hasErrors()) {
			model.addAttribute("oldUser", userRepo.findById((long)id).get());
			
			System.out.println(res);
			
			return "admin/user/updateUser";
		}
		
		userService.updateUser(user,(long) id);
		
		return "redirect:/";
	}
	
	//###### Load Person data in view ######
	@GetMapping(value = "/selectPersonUpdate")
	public String selectPersonToUpdateGet(Person person, Model model) {
		model.addAttribute("allPersons", personService.selectAll());
		
		return "admin/person/selectPersonUpdate";
	}
	
	//###### Use Person data from view ######
	@PostMapping(value = "selectPersonUpdate")
	public String selectPersonToUpdatePost(Person person, Model model) {
		return "redirect:/admin/updatePerson/" + person.getP_id();
	}
	
	//###### Load Person data to Update in view ######
	@GetMapping(value = "/updatePerson/{id}")
	public String updatePersonGet(@PathVariable(name = "id") int id, Person person, Model model) {
		model.addAttribute("oldPerson", personRepo.findById((long)id).get());
		model.addAttribute("genderTypes", Gender.values());
		
		return "admin/person/updatePerson";
	}
	
	//###### Use Person data to Update from view ######
	@PostMapping(value = "/updatePerson/{id}")
	public String updatePersonPost(@PathVariable(name = "id")int id, @Valid Person person, BindingResult res, Model model) {
		if(res.hasErrors()) {
			model.addAttribute("oldPerson", personRepo.findById((long)id).get());
			model.addAttribute("genderTypes", Gender.values());
			
			return "admin/person/updatePerson";
		}
		
		personService.updatePerson(person,(long) id);
		
		return "redirect:/";
	}
//###### END ### Update User & Person ### END ######

//###### Delete User & Person ######
	//###### Load User data in view ######
	@GetMapping(value = "/selectUserDelete")
	public String selectUserToDeleteGet(User user,Model model) {
		model.addAttribute("allUsers", userService.selectAll());
		
		return "admin/user/selectUserDelete";
	}
	
	//###### Delete User ######
	@PostMapping(value = "/selectUserDelete")
	public String selectUserToDeletePost(User user, Model model) {
		userService.deleteUser(user.getU_id());		
		return "redirect:/";
	}
	
	//###### Load Person data in view ######
	@GetMapping(value = "/selectPersonDelete")
	public String selectPersonToDeleteGet(Person person, Model model) {
		model.addAttribute("allPersons", personService.selectAll());
		return "admin/person/selectPersonDelete";
	}
	
	//###### Use Person data from view ######
	@PostMapping(value = "selectPersonDelete")
	public String selectPersonToDeletePost(Person person, Model model) {
		personService.deletePerson(person.getP_id());
		return "redirect:/";
	}
}
