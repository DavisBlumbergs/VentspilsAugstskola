package com.example.WEB.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Products")
public class Product {
	
	@Column(name = "Prod_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long prod_id;
	
	@Column(name = "Prod_Name")
	@NotNull
	private String name;
	
	@Column(name = "Type")
	@NotNull
	private ProductType productType;
	
	@Column(name = "Color")
	private String color;
	
	@Column(name = "Count")
	@NotNull
	@Min(0)
	private int count;
	
	@Column(name = "Material")
	private String material;
	
	@Column(name = "Description")
	private String description;
	
	public Product() {
		
	}
	
	public Product(String name, ProductType productType, String color, int count, String material, String description) {
		this.name = name;
		this.productType = productType;
		this.color = color;
		this.count = count;
		this.material = material;
		this.description = description;
	}
	
	public Product(String name, ProductType productType, int count, String description) {
		this.name = name;
		this.productType = productType;
		this.count = count;
		this.description = description;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product Name:" + name + " ,Available:" + count;
	}
	
}
