package com.pradeep.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.accounts.UserAccount;
import com.pradeep.exception.ExceptionCodes;
import com.pradeep.exception.InsufficentBalanceException;
import com.pradeep.repository.TransactionLogRepoWrapper;
import com.pradeep.responses.Status;
import com.pradeep.responses.TransactionResponse;
import com.pradeep.utils.AccountNumberGenerator;

@Service
public class TransferService {

	@Autowired
	TransactionLogRepoWrapper logRepositoryWrapper;

	public TransactionResponse transferFund(UserAccount fromAccount, UserAccount toAccount, Long amount) {
		// Lock ordering to avoid deadlocks based on AccoundId
		if (fromAccount.getId() < toAccount.getId()) {
			synchronized (fromAccount) {
				synchronized (toAccount) {
					return doTransferInternal(fromAccount, toAccount, amount);
				}
			}
		} else if (fromAccount.getId() > toAccount.getId()) {
			synchronized (toAccount) {
				synchronized (fromAccount) {
					return doTransferInternal(fromAccount, toAccount, amount);
				}
			}
		} else {
			// Will never happen as Account Validation will ensure fromAccount and toAccount
			// are different
			throw new RuntimeException("From account and to account are same");
		}

	}

	private TransactionResponse doTransferInternal(UserAccount fromAccount, UserAccount toAccount, Long amount) {
		Long txnId = AccountNumberGenerator.generateTransactionID();
		logRepositoryWrapper.storeTxnData(txnId, fromAccount.getName(), amount, "INITIATED");
		try {
			fromAccount.debit(amount, txnId);
		} catch (InsufficentBalanceException e) {
			logRepositoryWrapper.storeTxnData(txnId, fromAccount.getName(), amount, "INSUFFICIENT_FUND");
			return TransactionResponse.builder().Amount(amount).fromAcount(fromAccount).toAccount(toAccount)
					.message(ExceptionCodes.INSUFFICIENT_FUND_RESP_MSG).status(Status.FAILED)
					.code(ExceptionCodes.InsufficientFundExceptionCode).build();
		}
		logRepositoryWrapper.storeTxnData(txnId, fromAccount.getName(), amount, "DEBITED");
		toAccount.credit(amount, txnId);
		logRepositoryWrapper.storeTxnData(txnId, toAccount.getName(), amount, "CREDITED");
		return TransactionResponse.builder().Amount(amount).fromAcount(fromAccount).toAccount(toAccount)
				.message(ExceptionCodes.TRANSFER_SUCCESS_RESP_MSG).status(Status.SUCCESS).code(ExceptionCodes.Success)
				.build();
	}

}
