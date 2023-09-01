package org.mercury.ClientAccountService.controller;

import org.mercury.ClientAccountService.bean.Company;
import org.mercury.ClientAccountService.dto.CompanyRequest;
import org.mercury.ClientAccountService.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CompanyController
 * @Description TODO
 * @Author katefu
 * @Date 8/31/23 10:46 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/client")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/all")
    public List<Company> getAllCompany(){
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id){
        return companyService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> addCompany(@RequestBody CompanyRequest companyRequest) {
        try {
            Company addedCompany = companyService.addCompany(companyRequest);
            if (addedCompany != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Company created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create company");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editCompany(@PathVariable int id, @RequestBody CompanyRequest companyRequest) {
        try {
            Company editedCompany = companyService.editCompany(id, companyRequest);
            if (editedCompany != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Company edited successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to edit company");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
