package com.example.startup.users.controller;

import com.example.startup.exception.ResourceNotFoundException;
import com.example.startup.users.repository.UserRepository;
import com.example.startup.users.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable (value = "id") Integer id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public User createNewUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @DeleteMapping("users/{id}")
    public void deleteUserById(@PathVariable (value = "id") Integer id)  throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));
        userRepository.delete(user);
    }

    @PutMapping("customers/{id}")
    public ResponseEntity<User> updateCustomerDetails(@PathVariable (value = "id") Integer id, @PathVariable @RequestBody User userDetails)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID" + id + " not found"));

        user.setDob(userDetails.getDob());
        user.setName(userDetails.getName());


        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
}
