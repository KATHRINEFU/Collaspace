package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName AccountUpdateStatusRequest
 * @Description TODO
 * @Author katefu
 * @Date 1/3/24 8:05 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountUpdateStatusRequest {
    private String status;
}
