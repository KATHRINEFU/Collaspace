package org.mercury.TeamService.dto;

import jakarta.persistence.Access;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mercury.TeamService.bean.Account;
import org.mercury.TeamService.bean.Employee;

import java.util.Date;
import java.util.List;

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
    private List<String> teamTypes;
//    private List<Employee> members;
//    private List<Account> accounts;
}
