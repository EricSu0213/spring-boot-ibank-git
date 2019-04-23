package com.example.ibank.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
	
	public static String ROLE_ADMIN = "ADMIN";
	
	public static String ROLE_USER = "USER";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
	
    String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
