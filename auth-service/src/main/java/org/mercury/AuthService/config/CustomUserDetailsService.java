package org.mercury.AuthService.config;

import org.mercury.AuthService.bean.UserCredential;
import org.mercury.AuthService.dao.UserCredentialDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ClassName CustomUserDetailsService
 * @Description TODO
 * @Author katefu
 * @Date 9/10/23 11:09 PM
 * @Version 1.0
 **/
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialDao userCredentialDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = userCredentialDao.findByEmployeeEmail(username);
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with email :" + username));
    }
}