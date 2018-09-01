package com.pradeep.controllers;

        import com.pradeep.accounts.UserAccount;
        import com.pradeep.domain.TransactionLog;
        import com.pradeep.payment.BankService;
        import com.pradeep.payment.TransactionHistoryService;
        import com.pradeep.payment.TransferService;
        import com.pradeep.payment.TransferServiceHandler;
        import com.pradeep.request.TransferRequest;
        import com.pradeep.responses.ResponseDetails;
        import com.pradeep.responses.Status;
        import com.pradeep.responses.UserResponseDetails;
        import lombok.extern.log4j.Log4j2;
        import org.apache.logging.log4j.ThreadContext;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.constraints.Size;
        import java.util.List;
        import java.util.UUID;

@RestController
@Log4j2
public class SpringRestController {

    private BankService bankService;
    private TransactionHistoryService transactionHistoryService;
    private TransferServiceHandler transferServiceHandler;

    @Autowired
    public SpringRestController(BankService bankService, TransactionHistoryService transactionHistoryService, TransferServiceHandler transferServiceHandler){
        this.bankService = bankService;
        this.transactionHistoryService=transactionHistoryService;
        this.transferServiceHandler=transferServiceHandler;
    }
    @RequestMapping(value = "/v1/createAccount", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseDetails createAccount(@RequestParam(required = true) @Size(min= 6, max = 20, message = "guest name length must be between 8 and 20")String guestName, @RequestParam(required = true ) Long intialDeposit){
        ThreadContext.clearMap();
        ThreadContext.put("txnId", UUID.randomUUID().toString());
        log.debug("Received Create Request for user {} and deposit {}", guestName.substring(guestName.length()-4,guestName.length()), intialDeposit);
        bankService.createAccount(guestName,intialDeposit);
        log.info("Finished Creating user");
        ResponseDetails response = ResponseDetails.builder().message("Success").status(Status.SUCCESS).build();
        return response;
    }

    @RequestMapping(value = "/v1/viewAccount/{name}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserResponseDetails viewAccount(@PathVariable(value="name") @Size(min= 6, max = 20, message = "guest name length must be between 8 and 20")String guestName){
        ThreadContext.clearMap();
        ThreadContext.put("txnId", UUID.randomUUID().toString());
        log.debug("Received View Request for user {} ", guestName.substring(guestName.length()-4,guestName.length()));
        UserAccount account = bankService.viewAccountDetials(guestName);
        log.info("Finished Viewing user");
        UserResponseDetails response = UserResponseDetails.builder().balance(account.getBalance()).name(account.getName()).id(account.getId()).build();
        return response;
    }

    @RequestMapping(value = "/v1/viewTxnHistory", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TransactionLog> viewTxnHistory(){
        ThreadContext.clearMap();
        ThreadContext.put("txnId", UUID.randomUUID().toString());
        log.debug("Received Txn history request " );
        return transactionHistoryService.getAllTransaction();

    }

    @RequestMapping(value = "/v1/transferFund", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseDetails transferFund(@RequestBody TransferRequest transferRequest){
        ThreadContext.clearMap();
        ThreadContext.put("txnId", UUID.randomUUID().toString());
        log.debug("Received funds transfer request {} ", transferRequest);
        return transferServiceHandler.handleTransfer(transferRequest.getFromAccount(),transferRequest.getToAccount(),transferRequest.getAmount());


    }

}
