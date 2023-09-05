package org.mercury.ClientService.service;

import org.mercury.ClientService.bean.Company;
import org.mercury.ClientService.bean.Contact;
import org.mercury.ClientService.dao.CompanyDao;
import org.mercury.ClientService.dao.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ContactService
 * @Description TODO
 * @Author katefu
 * @Date 9/1/23 12:07 AM
 * @Version 1.0
 **/

@Service
public class ContactService {
    @Autowired
    private ContactDao contactDao;

    @Autowired
    private CompanyDao companyDao;

    public Contact getById(int id) {
        return contactDao.findById(id).orElse(null);
    }

    public List<Contact> getByCompanyId(int id) {
        Optional<Company> optionalCompany = companyDao.findById(id);
        if(optionalCompany.isEmpty()) return null;
        return contactDao.findAllByCompany(optionalCompany.get());
    }
}
