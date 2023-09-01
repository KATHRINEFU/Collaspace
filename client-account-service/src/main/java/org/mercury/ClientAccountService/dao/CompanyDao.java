package org.mercury.ClientAccountService.dao;

import org.mercury.ClientAccountService.bean.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDao extends JpaRepository<Company, Integer> {
}
