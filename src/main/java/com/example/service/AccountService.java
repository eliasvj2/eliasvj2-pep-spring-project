package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    public Account persistAccount(Account account){
        return accountRepository.save(account);
    }


    public Account registerAccount(Account account){
        boolean value = accountRepository.existsByUsername(account.getUsername());
        if(!account.getUsername().equals("") && account.getPassword().length() >= 4 && value == false ){
            accountRepository.save(account);
        }
        return accountRepository.findAccountByUsername(account.getUsername());
    }
    public String accountExists(Account account){
        boolean inDatabase = accountRepository.existsByUsername(account.getUsername());
        if(inDatabase == false){
            return "1";
        }
        return "2";
    }

    public Account loginAccount(Account account){
        Account inDatabase = accountRepository.findAccountByUsername(account.getUsername());
        if(inDatabase == null){
            return null;
        }
        if(account.getUsername().equals(account.getUsername()) && account.getPassword().equals(inDatabase.getPassword())){
            return inDatabase;
        }
        return null;
    }
}
