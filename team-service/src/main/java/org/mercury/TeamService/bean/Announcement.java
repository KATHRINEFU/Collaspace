package org.mercury.TeamService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Announcement
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 10:49 PM
 * @Version 1.0
 **/

@Entity
@Table(name = "ANNOUNCEMENT")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Announcement {
    @Id
    @SequenceGenerator(name = "announcement_seq_gen", sequenceName = "ANNOUNCEMENT_ANNOUNCEMENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="announcement_seq_gen", strategy = GenerationType.AUTO)
    private int announcementId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Team team;

    @Column
    private int announcementCreator;

    @Column
    private Date announcementCreationdate;

    @Column
    private String announcementContent;
}
