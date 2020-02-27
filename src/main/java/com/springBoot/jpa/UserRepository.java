package com.springBoot.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

// the class -> is which class i will manage.. and the second is the type of the id field..
public interface UserRepository extends CrudRepository<User , Long>{
	// 
	List<User> findByRole(String role);
}
