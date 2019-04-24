package com.example.ibank.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.ibank.entity.Account;
import com.example.ibank.service.AccountService;

@Controller
public class IndexController {
	
	@Autowired
    private AccountService accountService;
    
    @RequestMapping(value={"/index"}, method = RequestMethod.GET)
    public ModelAndView indexPage(Model model, Authentication authentication){
    	
    	Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
    	ModelAndView modelAndView = new ModelAndView();
    	
    	if (roles.contains("ADMIN")){		
	        modelAndView.setViewName("redirect:/admin/userList");
    	}
    	else {
    		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	    	
	    	String email = userDetails.getUsername();
	    	Account account = accountService.findByEmail(email);
	    	
	    	Long amount = account.getBalance();
	    	
	        modelAndView.addObject("amount", amount);
	        modelAndView.addObject("name", account.getName());		
	        modelAndView.setViewName("index");
    	}
    	
        return modelAndView;
        
    }
}
