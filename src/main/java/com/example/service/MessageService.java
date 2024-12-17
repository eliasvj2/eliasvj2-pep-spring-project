package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.*;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }


    public Message persistMessage(Message message){
        return messageRepository.save(message);
    }
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    public Message getMessageById(Message message){
        Account existingUser = accountRepository.getById(message.getPostedBy());
        if(existingUser.getUsername().equals(null)){
            return null;
        }
        if(!message.getMessageText().equals("") && message.getMessageText().length() <=255 && message.getPostedBy().equals(existingUser.getAccountId())){
            return persistMessage(message);
        }
        return null;
    }
}
