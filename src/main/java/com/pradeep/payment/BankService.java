package com.pradeep.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.accounts.UserAccount;
import com.pradeep.exception.UserAlreadyExistsException;
import com.pradeep.exception.UserNotFoundException;
import com.pradeep.repository.UserRepository;
import com.pradeep.utils.AccountNumberGenerator;

@Service
public class BankService {

    private UserRepository userRepository;


    @Autowired
    public BankService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAccount(String name, Long balance) {
        UserAccount userAccount = userRepository.fetch(name);
        if (userAccount != null) {
            throw new UserAlreadyExistsException("Guest Already Exists");
        }
        userAccount = new UserAccount(name, balance, AccountNumberGenerator.createAccountNumber());
        userRepository.insert(userAccount);
    }

    public UserAccount viewAccountDetials(String name) {
        UserAccount userAccount = userRepository.fetch(name);
        if (userAccount == null) {
            throw new UserNotFoundException("Guest doesn't Exist");
        }
        return userAccount;

    }
}