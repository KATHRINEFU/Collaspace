package org.mercury.EventService.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Team
 * @Description TODO
 * @Author katefu
 * @Date 8/29/23 12:45 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "TEAM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    private int teamId;

    @Column
    private String teamName;

    @Column
    private int teamCreator;

    @Column
    private Date teamCreationdate;

    @Column
    private String teamType;

    @Column
    private Integer teamDepartmentId;
}
