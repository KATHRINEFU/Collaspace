package org.mercury.EventService.dao;

import org.mercury.EventService.bean.MeetingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MeetingEventDao extends JpaRepository<MeetingEvent, Integer>, JpaSpecificationExecutor<MeetingEvent> {
}
