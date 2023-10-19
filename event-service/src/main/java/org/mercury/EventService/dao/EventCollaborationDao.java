package org.mercury.EventService.dao;

import org.mercury.EventService.bean.EventCollaboration;
import org.mercury.EventService.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCollaborationDao extends JpaRepository<EventCollaboration, Integer> {
    List<EventCollaboration> findAllByTeam(Team team);
}
