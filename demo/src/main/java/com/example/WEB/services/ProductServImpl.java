package com.example.WEB.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WEB.models.Product;
import com.example.WEB.models.ProductType;
import com.example.WEB.repos.ProductRepo;

@Service
public class ProductServImpl implements ProductServ {

	@Autowired
	ProductRepo productRepo;
	
	@Override
	public boolean addNewProduct(Product product) {
		if(productRepo.existsByName(product.getName())) {
			return false;
		}
		
		productRepo.save(product);
		return true;
	}

	@Override
	public List<Product> selectAll() {
		return (List<Product>) productRepo.findAll();
	}

	@Override
	public Product selectById(long id) {
		Product productTemp = productRepo.findById(id).get();
		if(productTemp != null && id >= 0)
			return productTemp;
		
		return null;
	}

	@Override
	public boolean updateProduct(Product product, long id) {
		if(productRepo.existsById(id) && product != null) {
			Product productTemp = productRepo.findById(id).get();
			
			productTemp.setName(product.getName());
			productTemp.setColor(product.getColor());
			productTemp.setCount(product.getCount());
			productTemp.setMaterial(product.getMaterial());
			productTemp.setProductType(product.getProductType());
			productTemp.setDescription(product.getDescription());
			
			productRepo.save(productTemp);
			
			return true;
		}

		return false;
	}

	@Override
	public boolean deleteProduct(long id) {
		Product productTemp = productRepo.findById(id).get();
		
		if(productTemp != null && id>= 0) {
			productRepo.delete(productTemp);
			return true;
		}
		return false;
	}

	@Override
	public boolean existsByName(String name) {
		if(productRepo.existsByName(name))
			return true;
		
		return false;
	}

	@Override
	public List<Product> selectByProductType(ProductType productType) {
		return (List<Product>) productRepo.findByProductType(productType);
	}
	
}
