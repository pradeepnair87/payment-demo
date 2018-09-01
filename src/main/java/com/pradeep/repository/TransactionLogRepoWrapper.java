package com.pradeep.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pradeep.domain.TransactionLog;

@Component
public class TransactionLogRepoWrapper {

    @Autowired
    TransactionLogRepository transactionLogRepository;

    public void storeTxnData(long txnId, String name, Long amount, String state){

        TransactionLog log =  new TransactionLog(txnId,name,amount,state);
      List<TransactionLog> logs =  transactionLogRepository.fetch(txnId);
      if(logs!=null && logs.size()>0){
          logs.add(log);
      }else{
          logs = new ArrayList<>();
          logs.add(log);
      }
        transactionLogRepository.insert(logs);
    }

}
