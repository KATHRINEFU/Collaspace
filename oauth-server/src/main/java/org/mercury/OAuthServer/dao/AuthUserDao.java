package org.mercury.OAuthServer.dao;

import org.mercury.OAuthServer.bean.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserDao extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByEmployeeEmail(String employeeEmail);
}
