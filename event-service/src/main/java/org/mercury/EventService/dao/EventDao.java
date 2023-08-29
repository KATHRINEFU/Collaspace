package org.mercury.EventService.dao;

import org.mercury.EventService.bean.Event;
import org.mercury.EventService.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EventDao extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findEventByEventType(String eventType);
    List<Event> findEventByEventCreator(int eventCreator);

    List<Event> findEventByTeam(Team team);
}
