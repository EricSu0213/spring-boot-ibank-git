package com.example.ibank.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ibank.entity.Account;
import com.example.ibank.service.AccountService;

@Controller
public class AccountController {
	
	@Autowired
    private AccountService accountService;
	
	@ResponseBody
	@RequestMapping(value="/deleteAccount", method = RequestMethod.GET)
    public Boolean deleteAccount() throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		
		if ((auth instanceof AnonymousAuthenticationToken)) {

            return Boolean.FALSE;
		}
		else {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();
			
			accountService.deleteAccount(email);
		}
		
        return Boolean.TRUE;
    }

	@ResponseBody
	@RequestMapping(value="/admin/deleteUser", method = RequestMethod.POST)
    public Boolean deleteUser(@RequestBody Account account, HttpServletRequest request) throws Exception {	
		
		String email = account.getEmail();
		accountService.deleteAccount(email);
		
        return Boolean.TRUE;
    }
}
