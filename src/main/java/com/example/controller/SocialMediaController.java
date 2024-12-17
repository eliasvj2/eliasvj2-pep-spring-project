package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        Account addedAccount = accountService.registerAccount(account);
        String statusType = accountService.accountExists(account);
        if(statusType.equals("1")){
            return ResponseEntity.status(200).body(addedAccount);
        }else if(statusType.equals("2")){
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
        Message messageId = messageService.getMessageById(message);
        if(messageId != null){
            return ResponseEntity.status(200).body(messageId);
        }
        return ResponseEntity.status(400).build(); 
    }

}
