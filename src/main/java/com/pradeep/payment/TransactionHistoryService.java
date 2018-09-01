package com.pradeep.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.TransactionLog;
import com.pradeep.repository.TransactionLogRepository;

@Service
public class TransactionHistoryService {

    TransactionLogRepository transactionLogRepository;
    @Autowired
    public TransactionHistoryService(TransactionLogRepository transactionLogRepository){
        this.transactionLogRepository=transactionLogRepository;
    }

   public List<TransactionLog> getAllTransaction(){
        return transactionLogRepository.fetchAll();
   }


}
