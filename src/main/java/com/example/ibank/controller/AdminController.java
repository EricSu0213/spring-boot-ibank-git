package com.example.ibank.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.ibank.entity.Account;
import com.example.ibank.entity.Role;
import com.example.ibank.service.AccountService;
import com.example.ibank.service.RoleService;

@Controller
public class AdminController {
	
	@Autowired
    private AccountService accountService;
	
	@Autowired
    private RoleService roleService;
	
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
    	
        modelAndView.setViewName("userListPage");
        
        return modelAndView;
        
    }
	
}
