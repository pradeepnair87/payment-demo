package com.pradeep.filters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pradeep.exception.ValidationException;
import com.pradeep.request.TransferRequest;

@Component
public class ValidationHelper {

	@Autowired
	public ValidationHelper(List<AccountValidationFilters> validationFiltersList) {
		this.validationFiltersList = validationFiltersList;
	}

	private List<AccountValidationFilters> validationFiltersList;

	public void validateAccounts(TransferRequest request) {
		ValidationResult result = null;
		for (AccountValidationFilters filter : validationFiltersList) {
			result = filter.validate(request);
			if (!result.isIsvalid())
				throw new ValidationException(result.reason);
			
		}

	}

}
