package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.*;
import com.example.service.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

public SocialMediaController(MessageService messageService, AccountService accountService){
    this.messageService = messageService;
    this.accountService = accountService;
}
    @GetMapping("/messages")
    public List<Message> messageHandler(){
        List<Message> messages = messageService.getAllMessages();
        return messages;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerHandler(@RequestBody Account account){
        String statusType = accountService.accountExists(account);
        Account addedAccount = accountService.registerAccount(account);
        
        if(statusType.equals("1") && addedAccount != null){
            return ResponseEntity.status(200).body(addedAccount);
        }else if(statusType.equals("2") && addedAccount != null){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginHandler(@RequestBody Account account){
        Account addedAccount = accountService.loginAccount(account);

        if(addedAccount != null){
            return ResponseEntity.status(200).body(addedAccount);
        }
        return ResponseEntity.status(401).build();
        
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> messageById(@RequestBody Message message){
        Message messageId = messageService.postMessage(message);
        if(messageId != null){
            return ResponseEntity.status(200).body(messageId);
        }
        return ResponseEntity.status(400).build(); 
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageHandler(@PathVariable Integer messageId){
        Message receivedMessage = messageService.getMessageById(messageId);

        return ResponseEntity.status(200).body(receivedMessage);

    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessageHandler(@PathVariable Integer messageId){
        Message receivedMessage = messageService.deleteMessageById(messageId);
        if(receivedMessage != null){
            return ResponseEntity.status(200).body("1");
        }

        return ResponseEntity.status(200).build();

    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserHandler(@PathVariable Integer accountId){
        List<Message> messages = messageService.getMessagesByUser(accountId);
        return ResponseEntity.status(200).body(messages);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessageHandler(@PathVariable Integer messageId, @RequestBody Message message){
        message.setMessageId(messageId);

        
            Message updatedMessage = messageService.updateMessageById(message);

            if(updatedMessage != null){

            System.out.println(updatedMessage);
            return ResponseEntity.status(200).body("1");}
            else{
                return ResponseEntity.status(400).build();
        
            }
       
            
        
    }

}
