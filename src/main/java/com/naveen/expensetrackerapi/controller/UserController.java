package com.naveen.expensetrackerapi.controller;

import com.naveen.expensetrackerapi.entity.User;
import com.naveen.expensetrackerapi.entity.UserModel;
import com.naveen.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.naveen.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> get() {
        return new ResponseEntity<User>(userService.findUser(), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> update(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> delete() throws ResourceNotFoundException {
        userService.deleteUser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
