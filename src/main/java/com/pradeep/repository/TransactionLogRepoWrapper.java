package com.pradeep.repository;

import com.pradeep.domain.TransactionLog;
import com.pradeep.domain.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
