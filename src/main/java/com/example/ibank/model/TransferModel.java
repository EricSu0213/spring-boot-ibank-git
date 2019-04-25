package com.example.ibank.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransferModel {
	
    @NotBlank(message="Email不可為空")
    @Email(message="Email不合法")
	String remoteEmail;
	
	@NotNull(message="請填寫金額")
	@Min(value = 0,message="輸入金額不可小於0")
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
