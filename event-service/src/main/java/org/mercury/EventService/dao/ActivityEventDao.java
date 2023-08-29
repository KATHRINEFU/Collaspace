package org.mercury.EventService.dao;

import org.mercury.EventService.bean.ActivityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName ActivityEventDao
 * @Description TODO
 * @Author katefu
 * @Date 8/27/23 8:32 PM
 * @Version 1.0
 **/
public interface ActivityEventDao extends JpaRepository<ActivityEvent, Integer>, JpaSpecificationExecutor<ActivityEvent> {
}
