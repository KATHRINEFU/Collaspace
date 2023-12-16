package org.mercury.EventService.bean;

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
 * @Date 12/15/23 4:15 PM
 * @Version 1.0
 **/
@Entity
@Table(name = "TEAM_MEMBER")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class TeamMember {
    @Id
    @SequenceGenerator(name = "team_member_seq_gen", sequenceName = "TEAM_MEMBER_TEAM_MEMBER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="team_member_seq_gen", strategy = GenerationType.AUTO)
    private int teamMemberId;

    private int teamTeamId;

    @Column
    private int employeeId;

    @Column
    private Date joindate;

    @Column
    private String role;

}
