package org.mercury.AccountService.bean;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Company
 * @Description TODO
 * @Author katefu
 * @Date 10/16/23 2:57 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Company {
    private int companyId;
    private String companyName;
    private String companyWebsite;
    private Date joindate;
    private String companyLogoUrl;
}
