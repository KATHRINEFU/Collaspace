package org.mercury.AuthService.dao;

import org.mercury.AuthService.bean.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialDao extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByEmployeeEmail(String employeeEmail);
}
