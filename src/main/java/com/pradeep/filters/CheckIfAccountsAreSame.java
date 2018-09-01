package com.pradeep.filters;

import org.springframework.stereotype.Component;

import com.pradeep.accounts.UserAccount;
import com.pradeep.exception.AccountValidationException;

@org.springframework.core.annotation.Order(value=1)
@Component
public class CheckIfAccountsAreSame implements TransactionValiationFilters {

    public ValidationResult validate(UserAccount fromAccount, UserAccount toAccountName) throws AccountValidationException{
        ValidationResult result =null;
        if(fromAccount.getName().equalsIgnoreCase(toAccountName.getName())){
            result = new ValidationResult(" From Account and To Account are same",false);
        }else{
            result = new ValidationResult( " From Account and To Account are different",true);
        }
        return result;
    }
}
