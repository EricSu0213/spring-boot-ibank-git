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
		
		Account account = accountService.findByEmail(email);
		
		Long balance = account.getBalance();
    	balance = balance + transaction.getAmount();
    	
    	accountService.updateBalanceByEmail(balance, email);
    	
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void withdraw(Transaction transaction, String email) throws Exception{
		
		Account account = accountService.findByEmail(email);
		
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
	
	@Transactional(rollbackFor=Exception.class)
	public void transfer(String userEmail, String remoteEmail, Long amount) throws Exception {
		
		Account remoteAccount = accountService.findByEmail(remoteEmail);
		Account userAccount = accountService.findByEmail(userEmail);
		
		if (remoteAccount == null) {
			throw new Exception("此帳戶不存在");
		}
		else {
			try {
				/////////扣款////////
				Long balance1 = userAccount.getBalance();
		    	balance1 = balance1 - amount;
		    	
		    	if (balance1.compareTo(0L) <0) {
		    		throw new Exception("餘額不足");
		    	}
		    	
		    	userAccount.setBalance(balance1);
		    	accountService.updateBalanceByEmail(balance1, userEmail);
		    	
		    	/////////加款////////
		    	Long balance2 = remoteAccount.getBalance();
		    	balance2 = balance2 + amount;
		    	
		    	remoteAccount.setBalance(balance2);
		    	accountService.updateBalanceByEmail(balance2, remoteEmail);
		    	
		    	/////////交易紀錄////////
		    	Transaction transaction1 = new Transaction();
		    	transaction1.setAccountEmail(userEmail);
		    	transaction1.setAmount(amount);
		    	transaction1.setDate(new Date());
		    	transaction1.setType(Transaction.TRASACTION_TRANSFER);
		    	transaction1.setRemoteEmail(remoteEmail);
		    	
		    	transactionRepository.save(transaction1);
		    	
		    	Transaction transaction2 = new Transaction();
		    	transaction2.setAccountEmail(remoteEmail);
		    	transaction2.setAmount(amount);
		    	transaction2.setDate(new Date());
		    	transaction2.setType(Transaction.TRASACTION_TRANSFER);
		    	transaction2.setRemoteEmail(userEmail);
		    	
		    	transactionRepository.save(transaction2);
			}
			catch(Exception e) {
				throw e;
			}
		}
		
	}
}
