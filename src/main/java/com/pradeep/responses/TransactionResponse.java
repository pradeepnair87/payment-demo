package com.pradeep.responses;

import com.pradeep.accounts.UserAccount;

import lombok.Getter;
import lombok.experimental.Builder;

@Builder
@Getter
public class TransactionResponse {
    String message;
    Status status;
    int code;
    UserAccount fromAcount;
    UserAccount toAccount;
    long Amount;
}
