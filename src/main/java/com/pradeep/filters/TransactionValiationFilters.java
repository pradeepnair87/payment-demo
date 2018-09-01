package com.pradeep.filters;

import com.pradeep.accounts.UserAccount;
import com.pradeep.exception.AccountValidationException;

public interface TransactionValiationFilters {
    public ValidationResult validate(UserAccount fromAccount, UserAccount toAccountName) throws AccountValidationException;
}
