package com.example.WEB.services;

import java.util.List;

import com.example.WEB.models.Person;

public interface PersonServ {
	
	boolean addNewPerson(Person person);
	
	List<Person> selectAll();
	
	Person selectById(long id);
	
	boolean updatePerson(Person person, long id);
	
	boolean deletePerson(long id);
}
