package com.example.ibank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ibank.entity.Account;
import com.example.ibank.repository.AccountRepository;

@Service("userService")
public class AccountService {
	    
	@Autowired
	private AccountRepository accountRepository;
    
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void saveAccount(Account account) {
		System.out.println(account.getPassword());
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
//		account.setDate(new Date());
		
		System.out.println(account.getPassword());
		
		accountRepository.save(account);
	}
	
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
	
    public void updateBalanceByEmail(Long balance, String email) {
    	accountRepository.updateBalanceByEmail(balance, email);
    }
    
    public void deleteByEmail(String email) {
    	accountRepository.deleteByEmail(email);
    }
    
}
