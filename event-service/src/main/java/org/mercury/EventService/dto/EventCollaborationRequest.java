package org.mercury.EventService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName EventCollaborationRequest
 * @Description TODO
 * @Author katefu
 * @Date 8/29/23 12:03 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventCollaborationRequest {
    private int teamId;
    private String teamRole;
    private int acceptStatus;
}
