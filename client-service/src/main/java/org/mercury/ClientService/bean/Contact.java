package org.mercury.ClientService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName Contact
 * @Description TODO
 * @Author katefu
 * @Date 9/1/23 12:02 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "CONTACT")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Contact {
    @Id
    @SequenceGenerator(name = "contact_seq_gen", sequenceName = "CONTACT_CONTACT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="contact_seq_gen", strategy = GenerationType.AUTO)
    private int contactId;

    @Column
    private String contactFirstname;

    @Column
    private String contactLastName;

    @Column
    private String contactEmail;

    @Column
    private String contactPhone;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Company company;

}
