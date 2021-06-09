package com.example.demo.controller;

import com.example.demo.model.LoginForm;
import com.example.demo.model.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginForm loginRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequest.getName());

        System.out.println(userDetails.toString());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

}
