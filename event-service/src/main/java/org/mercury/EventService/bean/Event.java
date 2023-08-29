package org.mercury.EventService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Event
 * @Description TODO
 * @Author katefu
 * @Date 8/25/23 12:48 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "EVENT")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @SequenceGenerator(name = "event_seq_gen", sequenceName = "EVENT_EVENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="event_seq_gen", strategy = GenerationType.AUTO)
    private int eventId;

    @Column
    private Date eventCreationdate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Team team;

    @Column
    private int eventCreator;

    @Column
    private String eventType;

    @Column
    private String eventTitle;

    @Column
    private String eventDescription;

    @Column
    private boolean eventExpired;

    @Column
    private Date eventLastUpdatedate;
}
