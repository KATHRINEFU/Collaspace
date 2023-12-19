package org.mercury.AuthService.service;

import io.jsonwebtoken.JwtException;
import org.mercury.AuthService.bean.UserCredential;
import org.mercury.AuthService.bean.UserLoginCredential;
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

    public void validateToken (String token) throws JwtException {
        jwtService.validateToken(token);
    }

    public String loginUser(UserLoginCredential credential) {
        UserCredential user =  userCredentialDao.findByEmployeeEmail(credential.getEmployeeEmail()).orElse(null);
        if(user==null) return null;
        String pwd = user.getEmployeePassword();
        String crePwd = passwordEncoder.encode(credential.getEmployeePassword());
        if(pwd.equals(crePwd)){
            return generateToken(user.getEmployeeEmail());
        }
        else return null;
    }

    public int getUserIdByUsername(String username) {
        UserCredential user =  userCredentialDao.findByEmployeeEmail(username).orElse(null);
        assert user != null; // happens after authenticated
        return user.getEmployeeId();
    }
}
