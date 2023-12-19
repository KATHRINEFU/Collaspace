package org.mercury.AuthService.controller;

import io.jsonwebtoken.JwtException;
import org.mercury.AuthService.bean.UserCredential;
import org.mercury.AuthService.bean.UserLoginCredential;
import org.mercury.AuthService.dto.*;
import org.mercury.AuthService.service.AuthService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AuthController
 * @Description TODO
 * @Author katefu
 * @Date 9/10/23 9:40 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public TokenResponse addNewUser(@RequestBody EmployeeRegistrationRequest request) {

        try {
            UserCredential user = new UserCredential(request.getEmployeePassword(), request.getEmployeeEmail());
            UserCredential addedUser = authService.saveUser(user);
            if (addedUser != null) {
                // TODO: async call to employee service create
                EmployeeCreationRequest creationRequest = new EmployeeCreationRequest(
                        request.getEmployeeFirstname(),
                        request.getEmployeeLastname(),
                        request.getEmployeeEmail(),
                        request.getEmployeeLocationCountry(),
                        request.getEmployeeLocationCity(),
                        request.getEmployeePhone(),
                        request.getDepartment(),
                        request.getEmployeeRole()
                );
                rabbitTemplate.convertAndSend("", "q.create-employee", creationRequest);

                String email = addedUser.getEmployeeEmail();
                String token = authService.generateToken(email);
                int id = authService.getUserIdByUsername(email);
                return new TokenResponse(id, email, token);

            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

//    @PostMapping
//    public ResponseEntity<String> loginUser(@RequestBody UserLoginCredential user){
//        try {
//            UserCredential validateUser = authService.loginUser(user);
//            if (validateUser != null) {
//                return ResponseEntity.status(HttpStatus.CREATED).body("User login successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to login user");
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody AuthRequest authRequest) {
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                String email = authRequest.getUsername();
                String token = authService.generateToken(email);
                int id = authService.getUserIdByUsername(email);
                return new TokenResponse(id, email, token);
            } else {
                return null;
            }
        }catch (IllegalArgumentException e) {
            return null;
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestBody ValidationRequest validationRequest) {
        try{
            authService.validateToken(validationRequest.getToken());
            return ResponseEntity.status(HttpStatus.CREATED).body("Token is valid");
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
