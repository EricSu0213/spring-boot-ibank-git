package com.example.ibank.model;

public class TransferModel {
	
	String remoteEmail;
	
	Long amount;

	public String getRemoteEmail() {
		return remoteEmail;
	}

	public void setRemoteEmail(String remoteEmail) {
		this.remoteEmail = remoteEmail;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
}
