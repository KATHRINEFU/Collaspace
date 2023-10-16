package org.mercury.TeamService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TeamAccount
 * @Description TODO
 * @Author katefu
 * @Date 10/16/23 10:13 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "TEAM_ACCOUNT")
public class TeamAccount {
    @Id
    @SequenceGenerator(name = "team_account_seq_gen", sequenceName = "TEAM_ACCOUNT_TEAM_ACCOUNT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="team_account_seq_gen", strategy = GenerationType.AUTO)
    private int teamAccountId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Team team;

    @Column
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

    @JsonIgnore
    public Team getTeam() {
        return team;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setTeamAccountId(int teamAccountId) {
        this.teamAccountId = teamAccountId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "TeamAccount{" +
                "teamAccountId=" + teamAccountId +
                ", team=" + team +
                ", accountId=" + accountId +
                '}';
    }
}
