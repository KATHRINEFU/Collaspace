package org.mercury.EventService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName EventEditRequest
 * @Description TODO
 * @Author katefu
 * @Date 1/3/24 6:44 PM
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEditRequest {

    // common fields
    private String eventDescription;
    private String eventType;

    // document event specific
    private String documentLink;
    private Date deadline;

    // meeting event specific
    private boolean meetingVirtual;
    private String meetingLocation;
    private String meetingLink;
    private String meetingNoteLink;
    private String meetingAgendaLink;
    private String meetingType;

    // activity event specific
    private boolean activityVirtual;
    private String activityLocation;

    @Override
    public String toString() {
        return "EventEditRequest{" +
                "eventDescription='" + eventDescription + '\'' +
                ", eventType='" + eventType + '\'' +
                ", documentLink='" + documentLink + '\'' +
                ", deadline=" + deadline +
                ", meetingVirtual=" + meetingVirtual +
                ", meetingLocation='" + meetingLocation + '\'' +
                ", meetingLink='" + meetingLink + '\'' +
                ", meetingNoteLink='" + meetingNoteLink + '\'' +
                ", meetingAgendaLink='" + meetingAgendaLink + '\'' +
                ", meetingType='" + meetingType + '\'' +
                ", activityVirtual=" + activityVirtual +
                ", activityLocation='" + activityLocation + '\'' +
                '}';
    }
}

