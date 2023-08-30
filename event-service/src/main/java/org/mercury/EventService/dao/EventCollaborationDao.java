package org.mercury.EventService.dao;

import org.mercury.EventService.bean.EventCollaboration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCollaborationDao extends JpaRepository<EventCollaboration, Integer> {
}
