package com.example.WEB.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Persons")
public class Person {
	
	@Column(name = "P_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long p_id;
	
	@Column(name = "P_Name")
	@Pattern(regexp = "[A-Z][a-z]*", message = "Please enter valid name")
	@NotNull
	private String name;
	
	@Column(name = "P_Surname")
	@Pattern(regexp = "[A-Z][a-z]*", message = "Please enter valid surname")
	@NotNull
	private String surname;
	
	@Column(name = "P_Age")
	@Min(13)
	@Max(110)
	private int age;
	
	@Column(name = "P_Gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "P_Country")
	@Pattern(regexp = "[A-Z][a-z]*", message = "Please enter valid country")
	@Size(min = 2, max = 10)
	private String country;
	
	@Column(name = "P_Email")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$",
    message = "Enter correct email. 'example@example.com'")
	@NotNull
	private String email;
	
	@OneToOne(mappedBy = "person")
	private User user;
	
	public Person() {
		
	}
	
	public Person(String name, String surname, int age, Gender gender, String country, String email) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.gender = gender;
		this.country = country;
		this.email = email;
	}
	
	public Person(String name, String surname, Gender gender, String country, String email) {
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.country = country;
		this.email = email;
	}
	
	public Person(String name, String surname, int age, String country, String email) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.country = country;
		this.email = email;
	}
	
	public Person(String name, String surname, String country, String email) {
		this.name = name;
		this.surname = surname;
		this.country = country;
		this.email = email;
	}

	public Long getP_id() {
		return p_id;
	}

	public void setP_id(Long p_id) {
		this.p_id = p_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
