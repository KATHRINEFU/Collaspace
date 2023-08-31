package org.mercury.TeamService.dao;

import org.mercury.TeamService.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDao extends JpaRepository<Team, Integer> {

}
