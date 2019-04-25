package com.example.ibank.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.ibank.entity.Transaction;
import com.example.ibank.model.TransferModel;
import com.example.ibank.service.TransactionService;

@Controller
public class TransactionController {
	
	@Autowired
    private TransactionService transactionService;
	
    @RequestMapping(value={"/depositPage"}, method = RequestMethod.GET)
    public ModelAndView depositPage(){
    	
        ModelAndView modelAndView = new ModelAndView();
        Transaction transaction = new Transaction();
		modelAndView.addObject("transaction", transaction);
        modelAndView.setViewName("/userViews/depositPage");
		
        return modelAndView;
        
    }
	
    @RequestMapping(value={"/withdrawPage"}, method = RequestMethod.GET)
    public ModelAndView withdrawPage(){
    	
        ModelAndView modelAndView = new ModelAndView();
        Transaction transaction = new Transaction();
		modelAndView.addObject("transaction", transaction);
        modelAndView.setViewName("/userViews/withdrawPage");
        
        return modelAndView;
        
    }
	
    @RequestMapping(value={"/transactionsPage"}, method = RequestMethod.GET)
    public ModelAndView transactionsPage(Authentication authentication, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        
    	Integer currentPage = page.orElse(1);
        Integer pageSize = size.orElse(10);
    	
        ModelAndView modelAndView = new ModelAndView();
        
        String email = authentication.getName();
        
//    	List<Transaction> transactions = transactionService.findByAccountEmailOrderByDateDesc(email);
    	Page<Transaction> transactionPage = transactionService.findPaginatedByAccountEmailAndActive(email, currentPage - 1, pageSize, true);
        
//		modelAndView.addObject("transactions", transactions);
    	modelAndView.addObject("transactions", transactionPage);
    	

        int totalPages = transactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
    	
        modelAndView.setViewName("/userViews/transactionsPage");
        
        return modelAndView;
        
    }
	
    @RequestMapping(value={"/transferPage"}, method = RequestMethod.GET)
    public ModelAndView transferPage(){
    	
        ModelAndView modelAndView = new ModelAndView();
        TransferModel transferModel = new TransferModel();
		modelAndView.addObject("transferModel", transferModel);
        modelAndView.setViewName("/userViews/transferPage");
        
        return modelAndView;
        
    }
    
    @RequestMapping(value={"/deposit"}, method = RequestMethod.POST)
    public ModelAndView deposit(Authentication authentication, @Valid Transaction transaction, BindingResult bindingResult){
    	
    	String email = authentication.getName();
    	
        ModelAndView modelAndView = new ModelAndView();
    	
    	if (bindingResult.hasErrors()) {
        	modelAndView.setViewName("/userViews/depositPage");
        }
        else {
        	transactionService.deposit(transaction, email);
            modelAndView.setViewName("redirect:/index");
        }
    	        
        return modelAndView;
        
    }
    
    @RequestMapping(value={"/withdraw"}, method = RequestMethod.POST)
    public ModelAndView withdraw(Authentication authentication, @Valid Transaction transaction, BindingResult bindingResult) throws Exception{
    	
    	String email = authentication.getName();
    	
        ModelAndView modelAndView = new ModelAndView();
    	
        if (bindingResult.hasErrors()) {
        	modelAndView.setViewName("/userViews/withdrawPage");
        }
        else {
	        try {
	    		transactionService.withdraw(transaction, email);
	            modelAndView.setViewName("redirect:/index");
	        }
	        catch(Exception e) {
	            modelAndView.setViewName("/userViews/withdrawPage");
	            modelAndView.addObject("exception", e.getMessage());
	        }
        }
	        
        return modelAndView;
        
    }
    
    @RequestMapping(value={"/transfer"}, method = RequestMethod.POST)
    public ModelAndView transfer(Authentication authentication, @Valid TransferModel transferModel, BindingResult bindingResult){
    	
    	String userEmail = authentication.getName();
    	
    	String RemoteEmail = transferModel.getRemoteEmail();
    	Long amount = transferModel.getAmount();
    	
    	ModelAndView modelAndView = new ModelAndView();
    	
    	if(!userEmail.equals(RemoteEmail)) {
	    	try {
	    		transactionService.transfer(userEmail, RemoteEmail, amount);
	    		modelAndView.setViewName("redirect:/index");
	    	}
	    	catch(Exception e) {
	    		modelAndView.addObject("exception", e.getMessage());
	    		modelAndView.setViewName("/userViews/transferPage");
	    	}
    	}
    	else {
    		modelAndView.addObject("transferModel", transferModel);
    		modelAndView.addObject("exception", "不可匯款給自己");
            modelAndView.setViewName("/userViews/transferPage");
    	}
    	        
        return modelAndView;
        
    }
    
}
