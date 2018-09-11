package com.pradeep.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Setter
@Getter
@Builder
public class TransactionLog {
	Long txnId;
	String name;
	Long amount;
	String state;

	public TransactionLog(Long txnId, String name, Long amount, String state) {
		this.txnId = txnId;
		this.name = name;
		this.amount = amount;
		this.state = state;
	}
}
