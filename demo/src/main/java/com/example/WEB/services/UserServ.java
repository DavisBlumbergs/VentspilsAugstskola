package com.example.WEB.services;

import java.util.List;

import com.example.WEB.models.User;

public interface UserServ {
	boolean addNewUser(User user);
	
	List<User> selectAll();
	
	User selectById(long id);
	
	boolean updateUser(User user, long id);
	
	boolean deleteUser(long id);
}
