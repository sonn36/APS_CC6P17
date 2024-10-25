package com.example.demo.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = repository.findAll();
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<User> findUserByFingerprint(@PathVariable Long id) {
        try {

            Optional<User> user = repository.findById(id);
            if (user.isPresent()){
                return ResponseEntity.ok().body(user.get());
            }
            else {
                throw new RuntimeException("Not Found");
            }


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        repository.save(user);

        return ResponseEntity.ok().body(user);

    }
}
