package com.example.WEB.services;

import java.util.List;

import com.example.WEB.models.Product;
import com.example.WEB.models.ProductType;

public interface ProductServ {
	boolean addNewProduct(Product product);
	
	List<Product> selectAll();
	
	Product selectById(long id);
	
	boolean existsByName(String name);
	
	boolean updateProduct(Product product, long id);
	
	boolean deleteProduct(long id);
	
	List<Product> selectByProductType(ProductType productType);
}
