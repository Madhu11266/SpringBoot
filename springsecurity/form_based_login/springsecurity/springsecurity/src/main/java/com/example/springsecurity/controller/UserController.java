package com.example.springsecurity.controller;

import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.service.UserService;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService,PasswordEncoder passwordEncoder){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.saveUser(user);
    }


    @GetMapping("/test")
    public String testMethood(){
        return "test";
    }

    @GetMapping("/test2")
    public String testMethood2(){
        return "test2";
    }

}
