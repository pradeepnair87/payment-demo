package com.pradeep.filters;

import org.springframework.stereotype.Component;

import com.pradeep.exception.AccountValidationException;
import com.pradeep.request.TransferRequest;

@org.springframework.core.annotation.Order(value = 2)
@Component
public class CheckIfAccountsAreSame implements AccountValidationFilters {

	
	@Override
	public ValidationResult validate(TransferRequest request) throws AccountValidationException {
		ValidationResult result = null;
		if (request.getFromAccount().equalsIgnoreCase(request.getToAccount())) {
			result = new ValidationResult(" From Account and To Account are same", false);
		} else {
			result = new ValidationResult(" From Account and To Account are different", true);
		}
		return result;
	}
}
