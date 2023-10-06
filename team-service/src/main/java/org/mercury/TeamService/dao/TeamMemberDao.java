package org.mercury.TeamService.dao;

import org.mercury.TeamService.bean.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName TeamMemberDao
 * @Description TODO
 * @Author katefu
 * @Date 10/5/23 10:21 AM
 * @Version 1.0
 **/
public interface TeamMemberDao extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findAllByEmployeeId(int employeeId);
}
