package org.mercury.TeamService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TeamAccountCreationRequest
 * @Description TODO
 * @Author katefu
 * @Date 1/4/24 12:52 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeamAccountCreationRequest {
    private int teamId;
    private int accountId;
}
