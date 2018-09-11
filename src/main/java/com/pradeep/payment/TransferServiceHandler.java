package com.pradeep.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pradeep.accounts.UserAccount;
import com.pradeep.exception.AccountValidationException;
import com.pradeep.exception.ExceptionCodes;
//import com.pradeep.filters.ValidationHelper;
import com.pradeep.filters.ValidationHelper;
import com.pradeep.repository.UserRepository;
import com.pradeep.request.TransferRequest;
import com.pradeep.responses.ResponseDetails;
import com.pradeep.responses.ResponseGenerator;
import com.pradeep.responses.Status;
import com.pradeep.responses.TransactionResponse;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TransferServiceHandler {

	private TransferService transferService;

	private UserRepository userRepository;

	private ValidationHelper validationHelper;

	@Autowired
	public TransferServiceHandler(TransferService transferService, UserRepository userRepository,
			ValidationHelper validationHelper) {
		this.transferService = transferService;
		this.userRepository = userRepository;
		this.validationHelper = validationHelper;
	}

	public ResponseDetails handleTransfer(TransferRequest request) {

		try {
			log.info("Basic Validations of Accounts ");
			validateRequest(request);
		} catch (AccountValidationException e) {
			log.error("Basic Validations Exception {}", e.getMessage());
			return ResponseGenerator.generateResponse(ExceptionCodes.ACCOUNT_VALIDATION_ERROR_RESP_MSG + e.getMessage(),
					Status.FAILED);
		}
		UserAccount fromAccount = userRepository.fetch(request.getFromAccount());
		UserAccount toAccount = userRepository.fetch(request.getToAccount());
		TransactionResponse transactionResponse = transferService.transferFund(fromAccount, toAccount, request.getAmount());
		return ResponseGenerator.generateResponse(transactionResponse);
	}

	private void validateRequest(TransferRequest request) {
		validationHelper.validateAccounts(request);
	}


}
