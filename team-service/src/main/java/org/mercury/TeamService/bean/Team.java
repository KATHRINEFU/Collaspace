package org.mercury.TeamService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Team
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 10:09 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "TEAM")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Team {
    @Id
    @SequenceGenerator(name = "team_seq_gen", sequenceName = "TEAM_TEAM_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="team_seq_gen", strategy = GenerationType.AUTO)
    private int teamId;

    @Column
    private String teamName;

    @Column
    private int teamCreator;

    @Column
    private Date teamCreationdate;

    @Column
    private String teamDescription;

    @Column
    private String teamType;

    @Column
    private Integer teamDepartmentId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "team")
    private List<TeamAccount> accounts;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "team")
    private List<TeamMember> members;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "team")
    private List<Announcement> announcements;
}
