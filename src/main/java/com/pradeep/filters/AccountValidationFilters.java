package com.pradeep.filters;


import com.pradeep.exception.AccountValidationException;

public interface AccountValidationFilters {

    public ValidationResult validate(String accountName) throws AccountValidationException;
}
