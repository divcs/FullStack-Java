package com.example.userservice.controller;

import org.springframework.web.bind.annotation.*;
import com.example.userservice.model.User;
import com.example.userservice.repository.InMemoryUserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private InMemoryUserRepo repo;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return repo.findById(id);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return repo.update(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = repo.delete(id);
        return deleted ? "User deleted" : "User not found";
    }
}
