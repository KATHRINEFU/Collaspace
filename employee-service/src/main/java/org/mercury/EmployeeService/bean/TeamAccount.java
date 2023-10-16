package org.mercury.EmployeeService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TeamAccount
 * @Description TODO
 * @Author katefu
 * @Date 10/16/23 10:57 AM
 * @Version 1.0
 **/

public class TeamAccount {
    private int teamAccountId;
    private Team team;
    private int accountId;

    public TeamAccount() {
    }

    public TeamAccount(int teamAccountId, Team team, int accountId) {
        this.teamAccountId = teamAccountId;
        this.team = team;
        this.accountId = accountId;
    }

    public int getTeamAccountId() {
        return teamAccountId;
    }

    public void setTeamAccountId(int teamAccountId) {
        this.teamAccountId = teamAccountId;
    }

    @JsonIgnore
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
