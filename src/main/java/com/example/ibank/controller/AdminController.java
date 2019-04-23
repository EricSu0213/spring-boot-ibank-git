package com.example.ibank.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.ibank.entity.Account;
import com.example.ibank.entity.Role;
import com.example.ibank.entity.Transaction;
import com.example.ibank.service.AccountService;
import com.example.ibank.service.RoleService;
import com.example.ibank.service.TransactionService;

@Controller
public class AdminController {
	
	@Autowired
    private AccountService accountService;
	
	@Autowired
    private RoleService roleService;
	
	@Autowired
    private TransactionService transactionService;
	
	@ResponseBody
	@RequestMapping(value="/admin/userList", method = RequestMethod.GET)
    public ModelAndView registration(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) throws Exception {

		Integer currentPage = page.orElse(1);
        Integer pageSize = size.orElse(10);
    	
        ModelAndView modelAndView = new ModelAndView();
        
        Role userRole = roleService.findByRole(Role.ROLE_USER);
        Page<Account> accountPage = accountService.findByRole(userRole, currentPage - 1, pageSize);
        
    	modelAndView.addObject("accountPage", accountPage);
    	
        int totalPages = accountPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
    	
        modelAndView.setViewName("/adminViews/userListPage");
        
        return modelAndView;
        
    }

	@ResponseBody
	@RequestMapping(value="/admin/deleteUser", method = RequestMethod.POST)
    public Boolean deleteUser(@RequestBody Account account, HttpServletRequest request) throws Exception {	
		
		String email = account.getEmail();
		accountService.deleteAccount(email);
		
        return Boolean.TRUE;
        
    }

	@ResponseBody
	@RequestMapping(value="/admin/transactionsPage", method = RequestMethod.GET)
    public ModelAndView transactionsPage(@RequestParam("email") String email, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, HttpServletRequest request) throws Exception {	
		
		Integer currentPage = page.orElse(1);
        Integer pageSize = size.orElse(10);
    	
        ModelAndView modelAndView = new ModelAndView();
        
//    	List<Transaction> transactions = transactionService.findByAccountEmailOrderByDateDesc(email);
    	Page<Transaction> transactionPage = transactionService.findPaginatedByAccountEmail(email, currentPage - 1, pageSize);
        
//		modelAndView.addObject("transactions", transactions);
    	modelAndView.addObject("transactions", transactionPage);
    	

        int totalPages = transactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("email", email);
        
        modelAndView.setViewName("/adminViews/transactionsPage");
        
        return modelAndView;
        
    }
	
}
