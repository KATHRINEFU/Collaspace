package org.mercury.EventService.bean;

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
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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
}
