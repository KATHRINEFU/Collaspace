package org.mercury.ClientService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Company
 * @Description TODO
 * @Author katefu
 * @Date 8/31/23 10:20 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "COMPANY")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Company {
    @Id
    @SequenceGenerator(name = "company_seq_gen", sequenceName = "COMPANY_COMPANY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="company_seq_gen", strategy = GenerationType.AUTO)
    private int companyId;

    @Column
    private String companyName;

    @Column
    private String companyWebsite;

    @Column
    private Date joindate;
}
