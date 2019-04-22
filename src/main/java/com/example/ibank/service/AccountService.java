package com.example.ibank.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ibank.entity.Account;
import com.example.ibank.entity.Role;
import com.example.ibank.repository.AccountRepository;
import com.example.ibank.repository.RoleRepository;

@Service("userService")
public class AccountService {
	    
	@Autowired
	private AccountRepository accountRepository;
    
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	public void saveAccount(Account account) {
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
//		account.setDate(new Date());
		Role userRole = roleRepository.findByRole("USER");
		account.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
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
    
    @Transactional(rollbackFor=Exception.class)
    public void deleteAccount(String email) throws Exception {
    	accountRepository.deleteAccountRole(email);
    	accountRepository.deleteByEmail(email);
		transactionService.deleteByAccountEmail(email);
    }
}
