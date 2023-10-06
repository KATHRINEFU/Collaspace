package org.mercury.EmployeeService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Annoucement
 * @Description TODO
 * @Author katefu
 * @Date 10/3/23 10:38 AM
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    private int announcementId;
    private Team team;
    private int announcementCreator;
    private Date announcementCreationdate;
    private String announcementContent;
}
