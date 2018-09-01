package com.pradeep.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pradeep.accounts.UserAccount;

@Repository
public class UserRepository implements BaseRepository<UserAccount>{


    private Map<String, UserAccount> repository;

    public UserRepository() {
        this.repository = new HashMap<>();
    }


    public void insert(UserAccount account){
        repository.put(account.getName(), account);
    }

    public UserAccount fetch(Long id ){

        return repository.get(id);
    }

    public UserAccount delete(Long id){
        UserAccount user = repository.get(id);
        if(user!=null)
        repository.remove(id);
    return user;
    }

    public UserAccount fetch(String name ){
        return repository.get(name);
    }

    public UserAccount delete(String  name){
        UserAccount user = repository.get(name);
        if(user!=null)
        repository.remove(name);
        return user;
    }
}
