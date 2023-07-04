package com.example.requestmanager.controller;

import com.example.requestmanager.dto.UserDto;
import com.example.requestmanager.model.User;
import com.example.requestmanager.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public TestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get")
    public List<User> getAll(){
        return userService.findAll();
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "something went wrong";
        userService.save(modelMapper.map(userDto, User.class));
        return "user has been registered";
    }
}
