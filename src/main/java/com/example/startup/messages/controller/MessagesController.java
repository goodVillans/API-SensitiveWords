package com.example.startup.messages.controller;


import com.example.startup.exception.ResourceNotFoundException;
import com.example.startup.messages.model.Message;
import com.example.startup.messages.repository.MessagesRepository;
import com.example.startup.sensitiveWords.service.CensorSensitiveWords;
import com.example.startup.users.model.User;
import com.example.startup.users.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessagesController {

   private final UserRepository userRepository;

   private final MessagesRepository messagesRepository;

   @Autowired
   private CensorSensitiveWords censorSensitiveWords;

   public MessagesController(UserRepository userRepository, MessagesRepository messagesRepository){
       this.userRepository = userRepository;
       this.messagesRepository = messagesRepository;
   }

   @GetMapping("/users/{id}/messages")
   public List<Message> getAllUserMessages(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException{
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));

       return user.getMessages();
   }

    @GetMapping("/users/{userId}/messages/{messageId}")
    public Message getMessageById(@PathVariable(value = "id")Integer id, @PathVariable Integer messageId) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));

        return messagesRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message with ID" + messageId + " not found"));
    }

   @PostMapping("/users/{id}/messages")
   public ResponseEntity<Message> postNewUserMessage(@PathVariable Integer id, @Valid @RequestBody Message message) throws ResourceNotFoundException{
       User user = userRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));

       message.setUser(user);

       // Application of Service(Aho-Corasick pattern matching algorithm used)
       String replaceSensitiveWords = CensorSensitiveWords.findAndReplaceSensitiveWords(message.getMessage());

       message.setMessage(replaceSensitiveWords);

       Message savedMessage = messagesRepository.save(message);

       return ResponseEntity.ok(savedMessage);
   }

   @DeleteMapping("/users/{userId}/messages/{messageId}")
   public void deleteMessage(@PathVariable(value = "id")Integer id, @PathVariable Integer messageId) throws ResourceNotFoundException {
       User user = userRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));

       Message message = messagesRepository.findById(messageId)
               .orElseThrow(() -> new ResourceNotFoundException("Message with ID" + messageId + " not found"));

       messagesRepository.delete(message);
   }

}
