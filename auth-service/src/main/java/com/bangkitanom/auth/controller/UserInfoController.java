package com.bangkitanom.auth.controller;

import com.bangkitanom.auth.dto.AuthRequest;
import com.bangkitanom.auth.entity.UserInfo;
import com.bangkitanom.auth.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bangkitanom.auth.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.save(userInfo);
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getName());
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @GetMapping("/validate-token")
    public String validateToken(@RequestParam("token") String token) {
        jwtService.validateToken(token);
        return "Token is valid";
    }
}
