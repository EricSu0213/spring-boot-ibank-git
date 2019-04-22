package com.example.ibank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ibank.service.AccountService;
import com.example.ibank.service.TransactionService;

@Controller
public class AccountController {
	
	@Autowired
    private AccountService accountService;
	
	@Autowired
    private TransactionService transactionService;
	
	@Transactional(rollbackFor=Exception.class)
	@ResponseBody
	@RequestMapping(value="/deleteAccount", method = RequestMethod.GET)
    public Boolean registration() throws Exception{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if ((auth instanceof AnonymousAuthenticationToken)) {

            return Boolean.FALSE;
		}
		else {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();
			
			accountService.deleteByEmail(email);
			transactionService.deleteByAccountEmail(email);
		}
		
        return Boolean.TRUE;
    }

}
