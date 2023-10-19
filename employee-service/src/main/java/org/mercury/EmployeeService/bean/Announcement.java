package org.mercury.EmployeeService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @ClassName Annoucement
 * @Description TODO
 * @Author katefu
 * @Date 10/3/23 10:38 AM
 * @Version 1.0
 **/
public class Announcement {
    private int announcementId;
    private Team team;
    private int announcementCreator;

    private String announcementCreatorName;
    private Date announcementCreationdate;
    private String announcementContent;

    public Announcement() {
    }

    public Announcement(int announcementId, Team team, int announcementCreator,String announcementCreatorName, Date announcementCreationdate, String announcementContent) {
        this.announcementId = announcementId;
        this.team = team;
        this.announcementCreator = announcementCreator;
        this.announcementCreatorName =  announcementCreatorName;
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

    public String getAnnouncementCreatorName() {
        return announcementCreatorName;
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

    public void setAnnouncementCreatorName(String announcementCreatorName) {
        this.announcementCreatorName = announcementCreatorName;
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

    @Override
    public String toString() {
        return "Announcement{" +
                "announcementId=" + announcementId +
                ", announcementCreator=" + announcementCreator +
                ", announcementCreatorName='" + announcementCreatorName + '\'' +
                ", announcementCreationdate=" + announcementCreationdate +
                ", announcementContent='" + announcementContent + '\'' +
                '}';
    }
}
