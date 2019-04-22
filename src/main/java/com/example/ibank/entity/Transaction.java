package com.example.ibank.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Transaction {
	
	public static String TRASACTION_WITHDRAW = "withdraw";
	
	public static String TRASACTION_DEPOSIT = "deposit";
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRASACTION_SEQ")
    @SequenceGenerator(sequenceName = "transaction_seq", allocationSize = 1, name = "TRASACTION_SEQ")
    Long id;
	
	String accountEmail;
	
	String type;
	
	@NotNull(message="請填寫金額")
	@Min(value = 0,message="輸入金額不可小於0")
	Long amount;
	
	@Column(name = "CREATED_DATE")
	Date date;

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
