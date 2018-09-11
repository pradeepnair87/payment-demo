package com.pradeep.filters;

import org.springframework.stereotype.Component;

import com.pradeep.exception.ValidationException;
import com.pradeep.request.TransferRequest;

@org.springframework.core.annotation.Order(value = 3)
@Component
public class IsAmountValid  implements AccountValidationFilters {

	@Override
	public ValidationResult validate(TransferRequest request) throws ValidationException {
		ValidationResult result = null;
		if (request.getAmount() <= 0) {
			result = new ValidationResult(" Incorrect Amount", false);
		} else {
			result = new ValidationResult( "Amount is correct", true);
		}
		return result;
	}

}
