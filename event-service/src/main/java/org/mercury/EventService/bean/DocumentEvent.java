package org.mercury.EventService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName DocumentEvent
 * @Description TODO
 * @Author katefu
 * @Date 8/25/23 8:51 AM
 * @Version 1.0
 **/
@Entity
@Table(name = "DOCUMENT_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEvent extends Event {
    @Column
    @SequenceGenerator(name = "document_event_seq_gen", sequenceName = "DOCUMENT_EVENT_DOCUMENT_EVENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="document_event_seq_gen", strategy = GenerationType.AUTO)
    private int documentEventId;

    @Column
    private String documentLink;

    @Column
    private Date deadline;

}
