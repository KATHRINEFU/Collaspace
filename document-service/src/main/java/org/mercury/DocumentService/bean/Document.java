package org.mercury.DocumentService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Document
 * @Description TODO
 * @Author katefu
 * @Date 1/2/24 11:07 PM
 * @Version 1.0
 **/

@Entity
@Table(name = "DOCUMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @SequenceGenerator(name = "document_seq_gen", sequenceName = "DOCUMENT_DOCUMENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="document_seq_gen", strategy = GenerationType.AUTO)
    private int documentId;

    @Column
    private String documentLink;

    @Column
    private String documentFromType;

    @Column
    private int ticketId;

    @Column
    private Date documentUploaddate;
}
