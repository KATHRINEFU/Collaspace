package org.mercury.ClientService.controller;

import org.mercury.ClientService.bean.Contact;
import org.mercury.ClientService.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ContactController
 * @Description TODO
 * @Author katefu
 * @Date 9/1/23 12:08 AM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable int id){
        return contactService.getById(id);
    }

    @GetMapping("/bycompany/id")
    public List<Contact> getContactByCompanyId(@PathVariable int id){
        return contactService.getByCompanyId(id);
    }
}
