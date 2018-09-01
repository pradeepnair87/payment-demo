package com.pradeep.filters;

import com.pradeep.accounts.BankAccount;
import com.pradeep.accounts.UserAccount;
import com.pradeep.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class ValidationHelper {

    @Autowired
    public ValidationHelper(List<AccountValidationFilters> validationFiltersList , List<TransactionValiationFilters> transactionValiationFilters) {
        this.validationFiltersList = validationFiltersList;
        this.transactionValiationFilters=transactionValiationFilters;
    }

    private List<AccountValidationFilters> validationFiltersList;
    private List<TransactionValiationFilters> transactionValiationFilters;


    public void validateAccounts(String fromName, String toName){
        ValidationResult result =null;
        for (AccountValidationFilters filter : validationFiltersList) {
            result= filter.validate(fromName);
            if(!result.isIsvalid())
                throw new ValidationException(result.reason);
            result=filter.validate(toName);
            if(!result.isIsvalid())
                throw new ValidationException(result.reason);
        }

    }

    public void validateAccounts(UserAccount fromName, UserAccount toName){
        ValidationResult result =null;
        for (TransactionValiationFilters filter : transactionValiationFilters) {
            result= filter.validate(fromName,toName);
            if(!result.isIsvalid())
                throw new ValidationException(result.reason);

        }
    }

public void validateAmount(Long amount){
        if(amount<0){
            throw new ValidationException("Amount is not valid!!");
        }
}

}
