package org.mercury.TeamService.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mercury.TeamService.bean.Team;

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
