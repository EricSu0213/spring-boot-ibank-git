package com.example.ibank.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ibank.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
	
	Account findByEmail(String email);

    @Query("select a from Account a where a.email = :email")
    Stream<Account> findByEmailReturnStream(@Param("email") String email);
    
    @Modifying
    @Transactional
    @Query(value = "update Account a set a.balance = :balance where a.email = :email", nativeQuery = true)
    void updateBalanceByEmail(@Param("balance") Long balance, @Param("email") String email);
    
    void deleteByEmail(String email);
    
    @Modifying
    @Transactional
    @Query(value = "delete from ACCOUNT_ROLE ar where ar.ACCOUNT_ID in (select a.ID from ACCOUNT a where a.EMAIL=?)", nativeQuery = true)
    void deleteAccountRole(String email);
}
