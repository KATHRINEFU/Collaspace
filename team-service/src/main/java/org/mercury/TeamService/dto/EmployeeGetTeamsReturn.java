package org.mercury.TeamService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mercury.TeamService.bean.Team;

import java.util.List;

/**
 * @ClassName EmployeeGetTeamsReturn
 * @Description TODO
 * @Author katefu
 * @Date 10/5/23 10:06 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeGetTeamsReturn {
    List<Team> teamList;
}
