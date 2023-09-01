package org.mercury.ClientAccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName CompanyRequest
 * @Description TODO
 * @Author katefu
 * @Date 8/31/23 11:22 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CompanyRequest {
    private String companyName;
    private String companyWebsite;
}
