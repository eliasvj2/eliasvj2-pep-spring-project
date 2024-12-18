package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Message getMessageById(Integer messageId){

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }
    public Message updateMessageById(Message message){
        
        Optional<Message> inDatabase = messageRepository.findById(message.getMessageId());
        if(inDatabase.isPresent()){
            Message oldObjectMessage = inDatabase.get();
            if(message.getMessageText().length() <=255 && !message.getMessageText().equals("")){
                oldObjectMessage.setMessageText(message.getMessageText());
                messageRepository.save(oldObjectMessage);
                return inDatabase.get();
            
            }

        }
        return null;
    }
    public Message deleteMessageById(Integer messageId){
        Boolean value = messageRepository.existsById(messageId);

        if(value == false){
            return null;
        }
        Message messagedeleted = messageRepository.getById(messageId);
        
        messageRepository.deleteById(messageId);

        return messagedeleted;
    }
    public Message postMessage(Message message){
        Boolean value = accountRepository.existsById(message.getPostedBy());

        if(value == false){
            return null;
        }

        if(!message.getMessageText().equals("") && message.getMessageText().length() <= 255){
            Message savedMessage = messageRepository.save(message);
            return savedMessage;
        }
        return null;
    }
    public List<Message> getMessagesByUser(Integer accountId){
        List<Message> messages = null;
        boolean value = messageRepository.existsByPostedBy(accountId);
        if(value == false){
            return messages = new ArrayList<>();
        }
        messages = messageRepository.findAllByPostedBy(accountId);
        return messages;
    }
}
