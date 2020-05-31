package com.example.WEB.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WEB.models.Person;
import com.example.WEB.repos.PersonRepo;
import com.example.WEB.repos.UserRepo;

@Service
public class PersonServImpl implements PersonServ{

	@Autowired
	PersonRepo personRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public boolean addNewPerson(Person person) {
		if(personRepo.existsByEmail(person.getEmail())) {
			return false;
		}
		
		personRepo.save(person);
		return true;
	}

	@Override
	public List<Person> selectAll() {
		return (List<Person>) personRepo.findAll();
	}

	@Override
	public Person selectById(long id) {
        Person personTemp = personRepo.findById(id).get();
        if(personTemp != null && id >= 0)
            return personTemp;

        return null;
	}

	@Override
	public boolean updatePerson(Person person, long id) {
        if(personRepo.existsById(id) && person != null)
        {
            Person personTemp = personRepo.findById(id).get();
            
            personTemp.setName(person.getName());
            personTemp.setSurname(person.getSurname());
            personTemp.setAge(person.getAge());
            personTemp.setGender(person.getGender());
            personTemp.setCountry(person.getCountry());
            personTemp.setEmail(person.getEmail());
            personTemp.setUser(person.getUser());       

            personRepo.save(personTemp);

            return true;
        }
        return false;
	}

	@Override
	public boolean deletePerson(long id) {
        Person personTemp = personRepo.findById(id).get();

        if(personTemp != null && id >= 0) {
        	userRepo.delete(personTemp.getUser());
            personRepo.delete(personTemp);
            return true;
        }
        return false;
	}

}
