package org.mercury.EmployeeService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Event
 * @Description TODO
 * @Author katefu
 * @Date 10/3/23 10:43 AM
 * @Version 1.0
 **/

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Event {
    private int eventId;
    private Date eventCreationdate;
    private int teamId;
    private String teamName;
    private int eventCreatorId;
    private String eventCreatorName;
    private String eventType;
    private String eventTitle;
    private String eventDescription;
    private boolean eventExpired;
    private Date eventLastUpdatedate;

    private String documentLink;
    private Date deadline;

    private boolean activityVirtual;
    private String activityLocation;
    private Date activityStarttime;
    private Date activityEndtime;

    private boolean meetingVirtual;
    private String meetingLocation;
    private Date meetingStarttime;
    private Date meetingEndtime;
    private String meetingNoteLink;
    private String meetingAgendaLink;
    private String meetingType;
}
