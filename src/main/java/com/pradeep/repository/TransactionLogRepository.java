package com.pradeep.repository;

import com.pradeep.accounts.UserAccount;
import com.pradeep.domain.TransactionLog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TransactionLogRepository implements BaseRepository<List<TransactionLog>>{
    private Map<Long, List<TransactionLog>> repository;

    public TransactionLogRepository() {
        this.repository = new ConcurrentHashMap<>();
    }


    public void insert(List<TransactionLog> logs){
        repository.put(logs.get(0).getTxnId(), logs);
    }

    public List<TransactionLog> fetch(Long id ){

        return (List<TransactionLog>)repository.get(id);
    }

    public List<TransactionLog> delete(Long id){
        return null;
    }

    public List<TransactionLog> fetch(String name ){
        return repository.get(name);
    }

    public List<TransactionLog> delete(String  name){

        return null;
    }

    public List<TransactionLog> fetchAll(){
        return repository.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
