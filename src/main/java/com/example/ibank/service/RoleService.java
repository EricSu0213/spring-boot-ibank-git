package com.example.ibank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ibank.entity.Role;
import com.example.ibank.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
    private RoleRepository roleRepository;
	
	public Role findByRole(String role) {
		return roleRepository.findByRole(role);
	}
	
}
