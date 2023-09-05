package org.mercury.ClientService.dao;

import org.mercury.ClientService.bean.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDao extends JpaRepository<Company, Integer> {
}
