package com.pradeep.accounts;

import com.pradeep.exception.InsufficentBalanceException;

import lombok.Getter;

@Getter
public class UserAccount implements BankAccount {
	private String name;
	private Long balance;
	private long id;

	public UserAccount(String name, Long balance, long accountNumber) {
		this.name = name;
		this.balance = balance;
		this.id = accountNumber;
	}

	public void debit(Long amount, long txnId) {

		if (balance < amount) {
			throw new InsufficentBalanceException("Insufficient fund!!");
		}
		this.balance = this.balance - amount;

	}

	public void credit(Long amount, long txnId) {
		this.balance = this.balance + amount;

	}

}
