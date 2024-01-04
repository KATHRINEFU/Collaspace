package org.mercury.TeamService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName TeamCreatorandSupervisorResponse
 * @Description TODO
 * @Author katefu
 * @Date 1/3/24 12:26 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeamCreatorandSupervisorResponse {
    List<Integer> ids;
}
