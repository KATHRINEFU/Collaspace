package org.mercury.TeamService.dao;

import org.mercury.TeamService.bean.TeamAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamAccountDao  extends JpaRepository<TeamAccount, Integer> {
}
