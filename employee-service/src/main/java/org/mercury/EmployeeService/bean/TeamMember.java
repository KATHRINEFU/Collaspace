package org.mercury.EmployeeService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName TeamMember
 * @Description TODO
 * @Author katefu
 * @Date 10/16/23 10:58 AM
 * @Version 1.0
 **/


public class TeamMember {
    private int teamMemberId;
    private Team team;
    private int employeeId;
    private Date joindate;
    private String role;

    public TeamMember() {
    }

    public TeamMember(int teamMemberId, Team team, int employeeId, Date joindate, String role) {
        this.teamMemberId = teamMemberId;
        this.team = team;
        this.employeeId = employeeId;
        this.joindate = joindate;
        this.role = role;
    }

    public int getTeamMemberId() {
        return teamMemberId;
    }

    public void setTeamMemberId(int teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    @JsonIgnore
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
