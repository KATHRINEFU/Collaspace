package org.mercury.TeamService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName TeamMember
 * @Description TODO
 * @Author katefu
 * @Date 10/5/23 10:10 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "TEAM_MEMBER")
public class TeamMember {
    @Id
    @SequenceGenerator(name = "team_member_seq_gen", sequenceName = "TEAM_MEMBER_TEAM_MEMBER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="team_member_seq_gen", strategy = GenerationType.AUTO)
    private int teamMemberId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Team team;

    @Column
    private int employeeId;

    @Column
    private Date joindate;

    @Column
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

    @Override
    public String toString() {
        return "TeamMember{" +
                "teamMemberId=" + teamMemberId +
                ", team=" + team +
                ", employeeId=" + employeeId +
                ", joindate=" + joindate +
                ", role='" + role + '\'' +
                '}';
    }

    public int getTeamMemberId() {
        return teamMemberId;
    }

    @JsonIgnore
    public Team getTeam() {
        return team;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Date getJoindate() {
        return joindate;
    }

    public String getRole() {
        return role;
    }

    public void setTeamMemberId(int teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
