package com.example.WEB.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.WEB.models.Person;
import com.example.WEB.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	
	boolean existsByUsername(String username);
	User findByUsername(String username);
	List<User> findByRole(boolean isAdmin);
	User findByPerson(Person person);
	
	User findByUsernameAndPassword(String username, String password);
}
