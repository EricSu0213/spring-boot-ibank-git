package com.example.ibank;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ibank.repository.AccountRepository;

@SpringBootApplication
public class IbankApplication {

    @Autowired
    DataSource dataSource;

    @Autowired
    AccountRepository accountRepository;

    public static void main(String[] args) {
		SpringApplication.run(IbankApplication.class, args);
	}
    
}
