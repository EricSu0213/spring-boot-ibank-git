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
	
	public static String TRASACTION_TRANSFER = "transfer";
	
	public static String TRASACTION_RECIPIENT = "recipient";
	
	@Id
	@Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRASACTION_SEQ")
    @SequenceGenerator(sequenceName = "transaction_seq", allocationSize = 1, name = "TRASACTION_SEQ")
    Long id;
	
	@Column(name = "ACCOUNT_EMAIL")
	String accountEmail;
	
	@Column(name = "TYPE")
	String type;
	
	@Column(name = "AMOUNT")
	@NotNull(message="請填寫金額")
	@Min(value = 0,message="輸入金額不可小於0")
	Long amount;
	
	@Column(name = "CREATED_DATE")
	Date date;
	
	@Column(name = "REMOTE_EMAIL")
	String remoteEmail;
	
	@Column(name = "ACTIVE")
	Boolean active;
	
	@Column(name = "BALANCE")
	Long balance;

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

	public String getRemoteEmail() {
		return remoteEmail;
	}

	public void setRemoteEmail(String remoteEmail) {
		this.remoteEmail = remoteEmail;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
}
