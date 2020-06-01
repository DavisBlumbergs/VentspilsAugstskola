package com.example.WEB.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.WEB.models.Gender;
import com.example.WEB.models.Person;
import com.example.WEB.models.Product;
import com.example.WEB.models.ProductType;
import com.example.WEB.models.User;
import com.example.WEB.repos.PersonRepo;
import com.example.WEB.repos.ProductRepo;
import com.example.WEB.repos.UserRepo;
import com.example.WEB.services.PersonServImpl;
import com.example.WEB.services.ProductServImpl;
import com.example.WEB.services.UserServImpl;

@Controller
public class TestDataController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PersonRepo personRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	UserServImpl userService;
	
	@Autowired
	PersonServImpl personService;
	
	@Autowired
	ProductServImpl productService;
	
	@GetMapping("/test")
	public String test(User user, Model model,Principal principal) {
		model.addAttribute("principal", principal);
		Person adminPerson = new Person("Davis", "Blumbergs",24,Gender.Male,"Latvia","s8_blumb_da@venta.lv");
		personService.addNewPerson(adminPerson);
		
		User admin = new User("admin", "administrator", adminPerson);
		userService.addNewUser(admin);
		
		Person userPerson = new Person("Valters", "Krasmanis",25,Gender.Male,"Latvia", "valcha@inbox.lv");
		personService.addNewPerson(userPerson);
		
		User users = new User("user","useruser", userPerson,true);
		userService.addNewUser(users);

		Product maika1 = new Product("T-krekls", ProductType.T_Shirt, "Black", 15, "koks", "Maika ar piedurknem, Made in Indonesia");
		Product maika2 = new Product("Apzimets T-Krels", ProductType.T_Shirt, "White", 1, "plastmasa", "Made in Taiwan");
		Product maika3 = new Product("V-Neck", ProductType.T_Shirt, "Purple", 7, "satins", "Made in China");
		Product maika4 = new Product("Tank Top", ProductType.T_Shirt, "Orange", 11, "ziids", "Made in Latvia");
		productService.addNewProduct(maika1);
		productService.addNewProduct(maika2);
		productService.addNewProduct(maika3);
		productService.addNewProduct(maika4);
		Product pants1 = new Product("Bikses", ProductType.Pants, 13, "labais");
		Product pants2 = new Product("Bikshels", ProductType.Pants, 10, "pusgaras");
		productService.addNewProduct(pants1);
		productService.addNewProduct(pants2);
		
		
		System.out.println(principal);
		
		
		return "test";
	}
}
