package com.example.ibank.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.ibank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
	
	List<Transaction> findByAccountEmailOrderByDateDesc(String email);
	
	void deleteByAccountEmail(String email);
	
	Page<Transaction> findByAccountEmail(String email, Pageable pageable);
	
	Page<Transaction>  findByAccountEmailAndActive(String email, Pageable pageable, Boolean active);
	
	@Modifying
    @Transactional
    @Query(value = "update Transaction t set t.active = :active where t.ACCOUNT_EMAIL = :accountEmail", nativeQuery = true)
    void updateActiveByAccountEmail(@Param("active") Boolean active, @Param("accountEmail") String accountEmail);
}
