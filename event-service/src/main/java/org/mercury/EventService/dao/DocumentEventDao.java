package org.mercury.EventService.dao;

import org.mercury.EventService.bean.DocumentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentEventDao extends JpaRepository<DocumentEvent, Integer>, JpaSpecificationExecutor<DocumentEvent> {
}
