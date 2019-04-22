package com.example.ibank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ibank.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByRole(String role);
	
}
