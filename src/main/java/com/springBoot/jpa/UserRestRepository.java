package com.springBoot.jpa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="users" , collectionResourceRel="users")
public interface UserRestRepository extends PagingAndSortingRepository<User , Long>{
	// if we want to expose this method in the restService is all we need is give  @param to the parameter name ..
	List<User> findByRole(@Param("role")String role);
}
