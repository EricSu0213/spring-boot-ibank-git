package com.example.ibank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.ibank.entity.Account;
import com.example.ibank.service.AccountService;

@Controller
public class LoginController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String login() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {

		    /* The user is logged in */
			return "redirect:/index";
		}
		
		return "login";
		
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Account account = new Account();
		modelAndView.addObject("account", account);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Account account, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Account accountExists = accountService.findAccountByEmail(account.getEmail());
        
        if (accountExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "這個email已有人使用");
        }
        
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
        	account.setBalance(0L);
            accountService.saveAccount(account);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("account", new Account());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }
	
}
