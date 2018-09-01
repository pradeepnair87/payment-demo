package com.pradeep.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class AccountNumberGenerator {

    static AtomicLong accountNumberSeed = new AtomicLong(1000000001);
    static AtomicLong transactionIdSeed = new AtomicLong(1111111111);
    public static long createAccountNumber(){
    return accountNumberSeed.getAndIncrement();
    }
    public static long generateTransactionID(){
        return transactionIdSeed.getAndIncrement();
    }

}
