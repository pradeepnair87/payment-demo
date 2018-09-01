package com.pradeep.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pradeep.exception.AccountValidationException;
import com.pradeep.repository.UserRepository;

@org.springframework.core.annotation.Order(value=1)
@Component
public class IsAccountValid implements AccountValidationFilters{

    private UserRepository userRepository;

    @Autowired
    public IsAccountValid(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ValidationResult validate(String accountName) throws AccountValidationException{
        ValidationResult result =null;
        if(userRepository.fetch(accountName)!=null){
            result = new ValidationResult(accountName+" Account Exists",true);
        }else{
            result = new ValidationResult(accountName+ " Account doesn't Exists",false);
        }
        return result;
    }
}
