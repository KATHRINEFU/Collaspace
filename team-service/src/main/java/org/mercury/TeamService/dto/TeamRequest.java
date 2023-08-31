package org.mercury.TeamService.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName TeamRequest
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 10:57 AM
 * @Version 1.0
 **/
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeamRequest {
    private String teamName;
    private int teamCreator;
    private String teamDescription;
    private String teamType;
}
