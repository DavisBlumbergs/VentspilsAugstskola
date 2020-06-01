package com.example.WEB.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WEB.models.User;
import com.example.WEB.repos.PersonRepo;
import com.example.WEB.repos.UserRepo;

@Service
public class UserServImpl implements UserServ{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PersonRepo personRepo;
	
	@Override
	public boolean addNewUser(User user) {
		if(user == null || userRepo.existsByUsername(user.getUsername())) {
			return false;
		}
		userRepo.save(user);
		return true;
	}

	@Override
	public List<User> selectAll() {	
		return (List<User>) userRepo.findAll();
	}

	@Override
	public User selectById(long id) {
		User userTemp = userRepo.findById(id).get();
		
		if(userTemp  != null && id >= 0)
		{
			return userTemp;
		}
		
		return null;
	}

	@Override
	public boolean updateUser(User user, long id) {
		if(userRepo.existsById(id) && user != null) {
			User userTemp = userRepo.findById(id).get();
			userTemp.setUsername(user.getUsername());
			userTemp.setPassword(user.getPassword());
			userTemp.setRole(user.getRole());
			userTemp.setPerson(userRepo.findById((long)id).get().getPerson());
			
			userRepo.save(userTemp);		
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUser(long id) {
		User userTemp = userRepo.findById(id).get();
		
		if(userTemp != null && id >= 0) {
			userRepo.delete(userTemp);
			personRepo.delete(userTemp.getPerson());
			return true;
		}
		return false;
	}

}
