package com.pradeep.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pradeep.exception.AccountValidationException;
import com.pradeep.repository.UserRepository;
import com.pradeep.request.TransferRequest;

@org.springframework.core.annotation.Order(value = 1)
@Component
public class IsAccountValid implements AccountValidationFilters {

	private UserRepository userRepository;

	@Autowired
	public IsAccountValid(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ValidationResult validate(TransferRequest request) throws AccountValidationException {
		ValidationResult result = null;
		String fromAccount = request.getFromAccount();
		String toAccount = request.getToAccount();
		if (fromAccount!=null && userRepository.fetch(fromAccount) != null) {
			result = new ValidationResult(fromAccount + " Account Exists", true);
		} else {
			return new ValidationResult(fromAccount + " Account doesn't Exists", false);
		}
		if (toAccount!=null && userRepository.fetch(toAccount) != null) {
			result = new ValidationResult(toAccount + " Account Exists", true);
		} else {
			return new ValidationResult(toAccount + " Account doesn't Exists", false);
		}
		return result;
	}
}
