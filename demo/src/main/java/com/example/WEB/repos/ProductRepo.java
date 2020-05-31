package com.example.WEB.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.WEB.models.Product;
import com.example.WEB.models.ProductType;

@Repository
public interface ProductRepo extends CrudRepository<Product, Long>{
	
	List<Product> findByProductType(ProductType productType);
	
	List<Product> findByCount(int count);
	List<Product> findByMaterial(String material);
	List<Product> findByColor(String color);
	
	Product findByName(String name);
	boolean existsByName(String name);
	
}
