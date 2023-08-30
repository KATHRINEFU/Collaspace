package org.mercury.EventService.service;

import org.mercury.EventService.bean.EventCollaboration;
import org.mercury.EventService.dao.EventCollaborationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName EventCollaborationService
 * @Description TODO
 * @Author katefu
 * @Date 8/29/23 9:45 PM
 * @Version 1.0
 **/

@Service
public class EventCollaborationService {
    @Autowired
    private EventCollaborationDao eventCollaborationDao;

    public void acceptEventCollaboration(int id) {
        Optional<EventCollaboration> collaborationOptional = eventCollaborationDao.findById(id);
        if(collaborationOptional.isEmpty()){
            throw new IllegalArgumentException("Event Collaboration Id Not Found");
        }
        EventCollaboration eventCollaboration = collaborationOptional.get();
        eventCollaboration.setAcceptStatus(true);
        eventCollaborationDao.save(eventCollaboration);
    }

    public EventCollaboration getById(int id) {
        return eventCollaborationDao.findById(id).orElse(null);
    }
}
