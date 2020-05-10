package com.example.WEB.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.WEB.models.Gender;
import com.example.WEB.models.Person;

@Repository
public interface PersonRepo extends CrudRepository<Person, Long> {

	List<Person> findByName(String name);
	List<Person> findBySurname(String surname);	
	Person findByEmail(String email);
	List<Person> findByCountry(String country);
	List<Person> findByAge(int age);
	List<Person> findByGender(Gender gender);
	
	boolean existsByEmail(String email);

}
