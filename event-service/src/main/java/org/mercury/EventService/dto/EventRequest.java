package org.mercury.EventService.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName EventRequest
 * @Description TODO
 * @Author katefu
 * @Date 8/27/23 8:06 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventRequest {

    // common fields
    private int eventCreationTeamId;
    private int eventCreator;
    private String eventType;
    private String eventTitle;
    private String eventDescription;
    private boolean eventExpired;

    // collaboration
    private List<EventCollaborationRequest> collaborations;

    // document event specific
    private String documentLink;
    private Date deadline;

    // meeting event specific
    private boolean meetingVirtual;
    private String meetingLocation;
    private Date meetingStarttime;
    private Date meetingEndtime;
    private String meetingNoteLink;
    private String meetingAgendaLink;
    private String meetingType;

    // activity event specific
    private boolean activityVirtual;
    private String activityLocation;
    private Date activityStarttime;
    private Date activityEndtime;
}
