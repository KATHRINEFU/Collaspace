package org.mercury.EventService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName EventCollaboration
 * @Description TODO
 * @Author katefu
 * @Date 8/29/23 11:00 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "EVENT_COLLABORATION")
public class EventCollaboration {
    @Id
    @SequenceGenerator(name = "event_collaboration_seq_gen", sequenceName = "EVENT_COLLABORATION_EVENT_COLLABORATION_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="event_collaboration_seq_gen", strategy = GenerationType.AUTO)
    private int eventCollaborationId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Team team;

    @Column
    private Date invitedate;

    @Column
    private boolean acceptStatus;

    @Column
    private String teamRole;

    public EventCollaboration() {
    }

    public EventCollaboration(int eventCollaborationId, Event event, Team team, Date invitedate, boolean acceptStatus, String teamRole) {
        this.eventCollaborationId = eventCollaborationId;
        this.event = event;
        this.team = team;
        this.invitedate = invitedate;
        this.acceptStatus = acceptStatus;
        this.teamRole = teamRole;
    }

    public int getEventCollaborationId() {
        return eventCollaborationId;
    }

    @JsonIgnore
    public Event getEvent() {
        return event;
    }

    public Team getTeam() {
        return team;
    }

    public Date getInvitedate() {
        return invitedate;
    }

    public boolean isAcceptStatus() {
        return acceptStatus;
    }

    public String getTeamRole() {
        return teamRole;
    }

    public void setEventCollaborationId(int eventCollaborationId) {
        this.eventCollaborationId = eventCollaborationId;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setInvitedate(Date invitedate) {
        this.invitedate = invitedate;
    }

    public void setAcceptStatus(boolean acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public void setTeamRole(String teamRole) {
        this.teamRole = teamRole;
    }


}
