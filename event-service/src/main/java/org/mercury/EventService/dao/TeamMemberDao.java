package org.mercury.EventService.dao;

import org.mercury.EventService.bean.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName TeamMemberDao
 * @Description TODO
 * @Author katefu
 * @Date 12/15/23 4:17 PM
 * @Version 1.0
 **/
public interface TeamMemberDao extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findAllByEmployeeId(int employeeId);
    List<TeamMember> findAllByTeamTeamId(int teamId);
}
