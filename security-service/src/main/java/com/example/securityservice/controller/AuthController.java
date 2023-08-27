package com.example.securityservice.controller;

import com.example.securityservice.dto.UserDto;
import com.example.securityservice.exception.UserNotFoundException;
import com.example.securityservice.model.User;
import com.example.securityservice.exception.InvalidTokenException;
import com.example.securityservice.service.JwtService;
import com.example.securityservice.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "something went wrong";
        userService.save(modelMapper.map(userDto, User.class));
        return "user has been registered";
    }

    @PostMapping("/token")
    public String getToken(@Valid @RequestBody UserDto userDto){
        User user = userService.findByUsername(userDto.getUsername()).orElseThrow(UserNotFoundException::new);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getUsername(), userDto.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(user);
        return "unable to generate token";
    }

    @GetMapping("/validate")
    public String isTokenValid(@RequestParam("token") String token) {
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username).orElse(null);
        if (user != null && jwtService.isJwtValid(token, user)) return "token is valid";
        throw new InvalidTokenException("invalid token");

    }


}
