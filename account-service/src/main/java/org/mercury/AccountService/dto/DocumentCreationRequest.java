package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName DocumentCreationRequest
 * @Description TODO
 * @Author katefu
 * @Date 1/2/24 11:28 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DocumentCreationRequest {
    private String documentLink;
    private String documentFromType;
    private int documentFromId;
}
