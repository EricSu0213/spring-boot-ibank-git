package com.example.ibank;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.ibank.repository.AccountRepository;

@SpringBootApplication
public class IbankApplication extends SpringBootServletInitializer{

    @Autowired
    DataSource dataSource;

    @Autowired
    AccountRepository accountRepository;

    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IbankApplication.class);
    }

    public static void main(String[] args) {
		SpringApplication.run(IbankApplication.class, args);
	}
    
}
