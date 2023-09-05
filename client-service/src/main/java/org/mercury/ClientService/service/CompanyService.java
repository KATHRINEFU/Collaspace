package org.mercury.ClientService.service;

import org.mercury.ClientService.bean.Company;
import org.mercury.ClientService.dao.CompanyDao;
import org.mercury.ClientService.dto.CompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CompanyService
 * @Description TODO
 * @Author katefu
 * @Date 8/31/23 10:45 PM
 * @Version 1.0
 **/

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public List<Company> getAll() {
        return companyDao.findAll();
    }

    public Company getById(int id) {
        return companyDao.findById(id).orElse(null);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Company addCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setCompanyWebsite(companyRequest.getCompanyWebsite());
        company.setJoindate(new Date());
        return companyDao.save(company);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Company editCompany(int id, CompanyRequest companyRequest) {
        Optional<Company> optionalCompany = companyDao.findById(id);
        if(optionalCompany.isEmpty()) return null;
        Company companyFromDB = optionalCompany.get();
        companyFromDB.setCompanyName(companyRequest.getCompanyName());
        companyFromDB.setCompanyWebsite(companyRequest.getCompanyWebsite());
        return companyDao.save(companyFromDB);
    }
}
