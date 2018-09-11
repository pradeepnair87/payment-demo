package com.pradeep.domain;

public enum TransactionType {
	CREDIT(1), DEBIT(2);
	int value;

	TransactionType(int value) {
		this.value = value;
	}
}
