package com.pradeep.payment;

import com.pradeep.accounts.UserAccount;
import com.pradeep.exception.AccountValidationException;
import com.pradeep.exception.ExceptionCodes;
import com.pradeep.filters.AccountValidationFilters;
import com.pradeep.filters.TransactionValiationFilters;
//import com.pradeep.filters.ValidationHelper;
import com.pradeep.filters.ValidationHelper;
import com.pradeep.repository.UserRepository;
import com.pradeep.responses.ResponseDetails;
import com.pradeep.responses.ResponseGenerator;
import com.pradeep.responses.Status;
import com.pradeep.responses.TransactionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class TransferServiceHandler {

    private TransferService transferService;

    private UserRepository userRepository;

    private ValidationHelper validationHelper;

    @Autowired
    public TransferServiceHandler(TransferService transferService, UserRepository userRepository, ValidationHelper validationHelper) {
        this.transferService=transferService;
        this.userRepository=userRepository;
        this.validationHelper=validationHelper;
    }

    public ResponseDetails handleTransfer(String fromName, String toName, Long amount){

        try {
            log.info("Basic Validations of Accounts {} {}",fromName,toName);
            basicValidation(fromName,toName);
        }catch(AccountValidationException e){
            log.error("Basic Validations Exception {}",e.getMessage());
            return ResponseGenerator.generateResponse(ExceptionCodes.ACCOUNT_VALIDATION_ERROR_RESP_MSG+e.getMessage(),Status.FAILED);
        }

        UserAccount fromAccount = userRepository.fetch(fromName);
        UserAccount toAccount = userRepository.fetch(toName);

        try {
            log.info("Validating Accounts {} {}",fromName,toName);
            tansactionValidation(fromAccount,toAccount);
            checkAmount(amount);
        }catch(AccountValidationException e){
            log.error("Validation Exception {}",e.getMessage());
            return ResponseGenerator.generateResponse(ExceptionCodes.ACCOUNT_VALIDATION_ERROR_RESP_MSG+e.getMessage(),Status.FAILED);
        }

        TransactionResponse transactionResponse = transferService.transferFund(fromAccount,toAccount,amount);

        return ResponseGenerator.generateResponse(transactionResponse);

    }

    private void basicValidation(String fromName, String toName){
         validationHelper.validateAccounts(fromName,toName);
    }
    private void tansactionValidation(UserAccount fromName, UserAccount toName){
         validationHelper.validateAccounts(fromName,toName);
    }
    private void checkAmount(long amount){
        validationHelper.validateAmount(amount);
    }

}
