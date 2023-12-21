package org.mercury.TeamService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName InviteMembersRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/20/23 8:04 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InviteMembersRequest {
    List<TeamMemberRequest> members;
}
