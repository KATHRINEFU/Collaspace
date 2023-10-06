package org.mercury.TeamService.bean;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
