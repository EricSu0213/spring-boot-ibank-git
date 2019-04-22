package com.example.ibank.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.ibank.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>, JpaRepository<Transaction,Long> ,JpaSpecificationExecutor<Transaction> {
	
	List<Transaction> findByAccountEmailOrderByDateDesc(String email);
	
	void deleteByAccountEmail(String email);
	
	Page<Transaction> findByAccountEmail(String email, Pageable pageable);
	
}
