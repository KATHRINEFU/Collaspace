package org.mercury.EventService.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName EventFilter
 * @Description TODO
 * @Author katefu
 * @Date 8/27/23 10:49 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventFilter {
    // general
    private String eventTitle;
    private String eventDescription;
    private Boolean eventExpired;
    private Date eventCreationdateStart;
    private Date eventCreationdateEnd;
    private Date eventLastUpdatedateStart;
    private Date eventLastUpdatedateEnd;
    private String eventType;

    // document specific
    private Date deadlineStart;
    private Date deadlineEnd;

    // meeting specific
    private Boolean meetingVirtual;
    private String meetingLocation;
    private Date meetingStarttimeStart;
    private Date meetingStarttimeEnd;
    private String meetingType;

    // activity specific
    private Boolean activityVirtual;
    private String activityLocation;
    private Date activityStarttimeStart;
    private Date activityStarttimeEnd;
}
