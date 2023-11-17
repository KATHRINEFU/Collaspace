package org.mercury.TeamService.dao;

import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.bean.TeamAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamAccountDao  extends JpaRepository<TeamAccount, Integer> {
    List<TeamAccount> findAllByTeam(Team team);
}
