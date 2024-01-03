package org.mercury.DocumentService.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
