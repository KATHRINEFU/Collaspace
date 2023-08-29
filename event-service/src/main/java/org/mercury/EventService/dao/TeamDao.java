package org.mercury.EventService.dao;

import org.mercury.EventService.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDao extends JpaRepository<Team, Integer> {
}
