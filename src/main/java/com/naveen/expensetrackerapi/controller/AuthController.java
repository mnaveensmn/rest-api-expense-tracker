package com.naveen.expensetrackerapi.controller;

import com.naveen.expensetrackerapi.entity.JwtResponse;
import com.naveen.expensetrackerapi.entity.LoginModel;
import com.naveen.expensetrackerapi.entity.User;
import com.naveen.expensetrackerapi.entity.UserModel;
import com.naveen.expensetrackerapi.security.CustomUserDetailsService;
import com.naveen.expensetrackerapi.service.UserService;
import com.naveen.expensetrackerapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginModel login) throws Exception {
        authenticate(login.getEmail(), login.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials");
        }
    }
}
