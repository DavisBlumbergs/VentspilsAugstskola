package com.example.WEB.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class User {
	
	@Column(name = "U_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long u_id;
	
	@Column(name = "Username")
	@Size(min = 3, max = 15)
	@NotNull
	private String username;
	
	@Column(name = "Password")
	@NotNull
	@Size(min = 8,max = 32)
	private String password;
	
	@Column(name = "isAdmin")
	private boolean role;
	
	@Valid
	@OneToOne
	@JoinColumn(name = "P_ID")
	private Person person;
	
	public User() {
		
	}
	
	public User(String username, String password, Person person) {
		this.username = username;
		this.password = password;
		this.person = person;
		
		this.role = false;
	}
	
	public User(String username, String password, Person person, boolean role) {
		this.username = username;
		this.password = password;
		this.person = person;
		
		this.role = role;
	}

	public Long getU_id() {
		return u_id;
	}

	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getRole() {
		return role;
	}

	public void setRole(boolean isAdmin) {
		this.role = isAdmin;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	
}
