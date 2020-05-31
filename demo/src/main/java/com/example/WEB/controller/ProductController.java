package com.example.WEB.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.WEB.models.Product;
import com.example.WEB.models.ProductType;
import com.example.WEB.repos.ProductRepo;
import com.example.WEB.services.ProductServImpl;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	ProductServImpl productService;
	
	@Autowired
	ProductRepo productRepo;
	
	@GetMapping(value = "/selectCategory")
	public String selectProductCategoryGet(Product product,Model model,Principal principal) {
		model.addAttribute("productTypes", ProductType.values());
		model.addAttribute("principal", principal);
		return "public/product/selectCategory";
	}
	
	@PostMapping(value = "/selectCategory")
	public String selectProductCategoryPost(Product product, Model model,Errors errors,Principal principal) {
		if(productRepo.findByProductType(product.getProductType()).size() == 0) {
			model.addAttribute("principal", principal);
			return "redirect:/products/selectCategory";
		}
		return "redirect:/products/selectCategory/" + product.getProductType();
	}
	
	@GetMapping(value = "/selectCategory/{type}")
	public String viewProductCategoryGet(@PathVariable(name = "type") ProductType type,Product product, Model model) {
		model.addAttribute("products", productRepo.findByProductType(type));
		return "public/product/viewCategory";
	}
	
	
	@PostMapping("/allProducts")
	public String listAllProductsPost(Product product, Model model) {	
		return "redirect:/products/allProducts/";
	}
	
	@GetMapping(value = "/product/{id}")
	public String listProduct(Product product, Model model) {
		return null;
	}
	
	

}
