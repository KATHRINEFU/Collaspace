package org.mercury.AuthService.service;

import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.JwtException;
import org.mercury.AuthService.bean.UserCredential;
import org.mercury.AuthService.bean.UserLoginCredential;
import org.mercury.AuthService.dao.UserCredentialDao;
import org.mercury.AuthService.dto.PasswordResetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private EmailService emailService;

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

    public int getUserIdByUsername(String username) {
        UserCredential user =  userCredentialDao.findByEmployeeEmail(username).orElse(null);
        assert user != null; // happens after authenticated
        return user.getEmployeeId();
    }

    public void sendPasswordReset(String email) {
        UserCredential user =  userCredentialDao.findByEmployeeEmail(email).orElse(null);
        if(user==null) throw new IllegalArgumentException("Email not exist");
        String token = UUID.randomUUID().toString();
        Date expirationTime = new Date(System.currentTimeMillis() + 1800000); // 30 minutes
        System.out.println("Setting user " + email + "with token " + token);
        user.setResetToken(token);
        user.setTokenExpirationDate(expirationTime);
        userCredentialDao.save(user);

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("action_url", "http://localhost:5174/reset-password?token="+token);
        placeholders.put("support_email", "yuehaofu207@gmail.com");
        emailService.sendEmail(
                "reset",
                email,
                "CollaSpace | Reset Password",
                placeholders);

    }

    public void resetPassword(PasswordResetRequest request) {
        UserCredential user = userCredentialDao.findByEmployeeEmail(request.getEmail()).orElse(null);
        if (user == null || user.getTokenExpirationDate().before(new Date())) {
            throw new IllegalArgumentException("Invalid or expired token.");
        }

        user.setEmployeePassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null); // Clear the token
        user.setTokenExpirationDate(null); // Clear the expiration date
        userCredentialDao.save(user);

    }
}
