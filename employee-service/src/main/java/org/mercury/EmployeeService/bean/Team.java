package org.mercury.EmployeeService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Team
 * @Description TODO
 * @Author katefu
 * @Date 10/3/23 10:37 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Team {
    private int teamId;
    private String teamName;
    private int teamCreator;
    private Date teamCreationdate;
    private String teamDescription;
    private String teamType;
    private Integer teamDepartmentId;
}
