package com.pradeep.filters;

import com.pradeep.exception.ValidationException;
import com.pradeep.request.TransferRequest;

public interface AccountValidationFilters {

	public ValidationResult validate(TransferRequest request) throws ValidationException;
}
