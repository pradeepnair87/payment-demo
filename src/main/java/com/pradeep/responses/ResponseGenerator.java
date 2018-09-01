package com.pradeep.responses;

public class ResponseGenerator {

    public static ResponseDetails generateResponse(String message, Status status){
        return ResponseDetails.builder().message(message).status(status).build();
    }

    public static ResponseDetails generateResponse(TransactionResponse transactionResponse){
        return ResponseDetails.builder().message(transactionResponse.getMessage()).status(transactionResponse.getStatus()).build();
    }
}
