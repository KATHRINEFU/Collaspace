package org.mercury.EventService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName ActivityEvent
 * @Description TODO
 * @Author katefu
 * @Date 8/25/23 9:20 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "ACTIVITY_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEvent extends Event{
    @Column
    @SequenceGenerator(name = "meeting_event_seq_gen", sequenceName = "MEETING_EVENT_MEETING_EVENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="meeting_event_seq_gen", strategy = GenerationType.AUTO)
    private int activityEventId;

    @Column
    private boolean activityVirtual;

    @Column
    private String activityLocation;

    @Column
    private Date activityStarttime;

    @Column
    private Date activityEndtime;

}
