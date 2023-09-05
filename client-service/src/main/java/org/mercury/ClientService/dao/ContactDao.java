package org.mercury.ClientService.dao;

import org.mercury.ClientService.bean.Company;
import org.mercury.ClientService.bean.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName ContactDao
 * @Description TODO
 * @Author katefu
 * @Date 9/1/23 12:06 AM
 * @Version 1.0
 **/
public interface ContactDao extends JpaRepository<Contact, Integer> {
    List<Contact> findAllByCompany(Company company);
}
