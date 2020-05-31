package com.example.WEB.controller;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.WEB.models.Gender;
import com.example.WEB.models.Product;
import com.example.WEB.models.ProductType;
import com.example.WEB.services.ProductServImpl;

@Controller
@RequestMapping(value = "/admin")
public class AdminProductController {

	@Autowired
	ProductServImpl productService;
	
	@GetMapping("/addProduct")
	public String addProductGet(Product product, Model model,Principal principal) {
		model.addAttribute("types", ProductType.values());
		model.addAttribute("principal", principal);
		return "admin/product/addProduct";
	}
	@PostMapping("/addProduct")
	public String addProductPost(@Valid Product product, Model model, BindingResult res,Principal principal) throws IOException {

		if(productService.existsByName(product.getName())) {
			model.addAttribute("types", ProductType.values());
			model.addAttribute("principal", principal);
			res.addError(new FieldError("name", "name", "this name already exists"));
			
			return "admin/product/addProduct";
		}  

        if (res.hasErrors()){
            model.addAttribute("types", Gender.values());
            return "register-login/register";
        }
        
        Product tempProduct = new Product(product.getName(),
        								product.getProductType(),
        								product.getColor(),
        								product.getCount(),
        								product.getMaterial(),
        								product.getDescription());
        
        productService.addNewProduct(tempProduct);
        
		return "redirect:/products/selectCategory";
	}
	
	@GetMapping("/selectProductDelete")
	public String deleteProductGet(Product product, Model model,Principal principal) {
		model.addAttribute("allProducts", productService.selectAll());
		model.addAttribute("principal", principal);
		return "admin/product/selectProductDelete";
	}
	@PostMapping("/selectProductDelete")
	public String deleteProductPost(Product product, Model model) {
		productService.deleteProduct(product.getProd_id());	
		return "redirect:/";
	}

	
	@GetMapping(value = "/selectProductUpdate")
	public String selectProductToUpdateGet(Product product, Model model,Principal principal) {
		model.addAttribute("allProducts", productService.selectAll());	
		model.addAttribute("principal", principal);
		return "admin/product/selectProductUpdate";
	}
	@PostMapping(value = "/selectProductUpdate")
	public String selectProductToUpdatePost(Product product, Model model,Principal principal) {
		return "redirect:/admin/updateProduct/" + product.getProd_id();
	}
	
	
	@GetMapping(value = "/updateProduct/{id}")
	public String updateProductGet(@PathVariable(name = "id") int id, Product product, Model model,Principal principal) {
		model.addAttribute("oldProduct", productService.selectById(id));
		model.addAttribute("principal", principal);
		model.addAttribute("productTypes", ProductType.values());
		
		return "admin/product/updateProduct";
	}
	
	@PostMapping(value = "/updateProduct/{id}")
	public String updateProductPost(@PathVariable(name = "id")int id, @Valid Product product, BindingResult res, Model model,Principal principal) {
		if(res.hasErrors()) {
			model.addAttribute("oldProduct", productService.selectById(id));
			model.addAttribute("pruductTypes", ProductType.values());
			model.addAttribute("principal", principal);
			System.out.println(res);
			
			return "admin/product/updateProduct";
		}
		
		productService.updateProduct(product,(long) id);
		
		return "redirect:/";
	}
}
