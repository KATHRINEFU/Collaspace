package org.mercury.TeamService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName InviteClientsRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/21/23 8:50 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InviteClientsRequest {
    List<Integer> accountIds;
}
