package org.mercury.AccountService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Account
 * @Description TODO
 * @Author katefu
 * @Date 9/4/23 6:42 PM
 * @Version 1.0
 **/

@Entity
@Table(name = "ACCOUNT")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Account {
    @Id
    @SequenceGenerator(name = "account_seq_gen", sequenceName = "ACCOUNT_ACCOUNT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="account_seq_gen", strategy = GenerationType.AUTO)
    private int accountId;

    @Column
    private String accountType;

    @Column
    private int companyId;

    @Column
    private String accountCurrentStatus;

    @Column
    private int accountCurrentResponsibleDepartmentId;

    @Column
    private int biddingPersonnel;

    @Column
    private int salesPersonnel;

    @Column
    private int solutionArchitectPersonnel;

    @Column
    private int CustomerSuccessPersonnel;

    @Column
    private Date accountCreationdate;

    @Column
    private Date accountLastUpdatedate;
}
