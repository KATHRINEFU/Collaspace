package org.mercury.AuthService.service;

import org.mercury.AuthService.bean.UserCredential;
import org.mercury.AuthService.dao.UserCredentialDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName AuthService
 * @Description TODO
 * @Author katefu
 * @Date 9/10/23 9:26 PM
 * @Version 1.0
 **/

@Service
public class AuthService {
    @Autowired
    private UserCredentialDao userCredentialDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserCredential saveUser(UserCredential credential){
        credential.setEmployeePassword(passwordEncoder.encode(credential.getEmployeePassword()));
        return userCredentialDao.save(credential);
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
