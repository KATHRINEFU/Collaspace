package org.mercury.EventService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName MeetingEvent
 * @Description TODO
 * @Author katefu
 * @Date 8/25/23 8:53 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "MEETING_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingEvent extends Event{

    @Column
    private boolean meetingVirtual;

    @Column
    private String meetingLocation;

    @Column
    private Date meetingStarttime;

    @Column
    private Date meetingEndtime;

    @Column
    private String meetingNoteLink;

    @Column
    private String meetingAgendaLink;

    @Column
    private String meetingType;
}
