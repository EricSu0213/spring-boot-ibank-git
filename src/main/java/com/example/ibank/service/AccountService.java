package com.example.ibank.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ibank.entity.Account;
import com.example.ibank.entity.Role;
import com.example.ibank.repository.AccountRepository;
import com.example.ibank.repository.RoleRepository;

@Service
public class AccountService {
	    
	@Autowired
	private AccountRepository accountRepository;
    
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	public void createAccount(Account account) {
		account.setBalance(0L);
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
		account.setDate(new Date());
		Role userRole = roleRepository.findByRole("USER");
		account.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		account.setActive(true);
		
		accountRepository.save(account);
	}
	
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
    
    public Account findByEmailAndActive(String email, Boolean active) {
    	return accountRepository.findByEmailAndActive(email, active);
    }
	
    public void updateBalanceByEmail(Long balance, String email) {
    	accountRepository.updateBalanceByEmail(balance, email);
    }
    
    public void deleteByEmail(String email) {
//    	accountRepository.deleteByEmail(email);
    	accountRepository.updateActiveByEmail(false, email);
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void deleteAccount(String email) throws Exception {
//    	accountRepository.deleteAccountRole(email);
//    	accountRepository.deleteByEmail(email);
    	try {
	    	accountRepository.updateActiveByEmail(false, email);
			transactionService.deleteByAccountEmail(email);
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    
    public Page<Account> findByRole(Role role, Integer page, Integer size) {
    	
    	Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "Date");
		
    	Set<Role> roles = new HashSet<Role>(Arrays.asList(role));
    	
		Page<Account> accountPage = accountRepository.findByRoles(roles, pageable);
		
		return accountPage;
    }
    
    public Page<Account> findByRoleAndActive(Role role, Integer page, Integer size, Boolean active) {
    	
    	Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "Date");
		
    	Set<Role> roles = new HashSet<Role>(Arrays.asList(role));
    	
		Page<Account> accountPage = accountRepository.findByRolesAndActive(roles, pageable, active);
		
		return accountPage;
    }
}
