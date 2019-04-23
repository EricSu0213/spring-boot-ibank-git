package com.example.ibank.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ibank.entity.Account;
import com.example.ibank.entity.Transaction;
import com.example.ibank.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
    private AccountService accountService;
	
	public void saveTransaction(Transaction transaction) {
		
		transactionRepository.save(transaction);
	
	}

	public List<Transaction> findByAccountEmailOrderByDateDesc(String email) {
		
		return transactionRepository.findByAccountEmailOrderByDateDesc(email);
	
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void deposit(Transaction transaction, String email) {
		
		transaction.setType(Transaction.TRASACTION_DEPOSIT);
    	transaction.setAccountEmail(email);
    	transaction.setDate(new Date());
		transactionRepository.save(transaction);
		
		Account account = accountService.findAccountByEmail(email);
		
		Long balance = account.getBalance();
    	balance = balance + transaction.getAmount();
    	
    	accountService.updateBalanceByEmail(balance, email);
    	
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void withdraw(Transaction transaction, String email) throws Exception{
		
		Account account = accountService.findAccountByEmail(email);
		
		Long balance = account.getBalance();
    	balance = balance - transaction.getAmount();
    	
    	if (balance.compareTo(0L) <0) {
    		throw new Exception("餘額不足");
    	}
		
		transaction.setType(Transaction.TRASACTION_WITHDRAW);
    	transaction.setAccountEmail(email);
    	transaction.setDate(new Date());
		transactionRepository.save(transaction);
    	
    	accountService.updateBalanceByEmail(balance, email);
    	
	}
	
	public void deleteByAccountEmail(String email) throws Exception {
		transactionRepository.deleteByAccountEmail(email);
	}
	
	public Page<Transaction> findPaginatedByAccountEmail(String email, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "Date");
		
		Page<Transaction> transactionPage = transactionRepository.findByAccountEmail(email, pageable);
		
		return transactionPage;
	}
	
}
