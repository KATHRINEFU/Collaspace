package org.mercury.TeamService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName AnnouncementRequest
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 11:18 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AnnouncementRequest {
    private int teamId;
    private int announcementCreator;
    private String announcementContent;
}
