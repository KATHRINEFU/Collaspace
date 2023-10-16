package org.mercury.TeamService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public Announcement() {
    }

    public Announcement(int announcementId, Team team, int announcementCreator, Date announcementCreationdate, String announcementContent) {
        this.announcementId = announcementId;
        this.team = team;
        this.announcementCreator = announcementCreator;
        this.announcementCreationdate = announcementCreationdate;
        this.announcementContent = announcementContent;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    @JsonIgnore
    public Team getTeam() {
        return team;
    }

    public int getAnnouncementCreator() {
        return announcementCreator;
    }

    public Date getAnnouncementCreationdate() {
        return announcementCreationdate;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setAnnouncementCreator(int announcementCreator) {
        this.announcementCreator = announcementCreator;
    }

    public void setAnnouncementCreationdate(Date announcementCreationdate) {
        this.announcementCreationdate = announcementCreationdate;
    }

    public void setAnnouncementContent(String announcementContent) {
        this.announcementContent = announcementContent;
    }
}
