package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName AccountAddDocumentsRequest
 * @Description TODO
 * @Author katefu
 * @Date 1/3/24 9:11 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountAddDocumentsRequest {
    List<String> files;
}
