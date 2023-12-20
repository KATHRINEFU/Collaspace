package org.mercury.EmployeeService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName UpdateProfileRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/19/23 6:49 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateProfileRequest {
    private int employeeId;
    private String newImageUrl;
}
