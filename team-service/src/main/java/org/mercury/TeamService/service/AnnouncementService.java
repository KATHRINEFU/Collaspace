package org.mercury.TeamService.service;

import org.mercury.TeamService.bean.Announcement;
import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.dao.AnnouncementDao;
import org.mercury.TeamService.dao.TeamDao;
import org.mercury.TeamService.dto.AnnouncementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName AnnoucementService
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 11:13 PM
 * @Version 1.0
 **/

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementDao announcementDao;

    @Autowired
    private TeamDao teamDao;

    public List<Announcement> getByTeamId(int teamId) {
        Optional<Team> optionalTeam = teamDao.findById(teamId);
        if(optionalTeam.isEmpty()) return null;
        return announcementDao.findAllByTeam(optionalTeam.get());
    }

    public Announcement getById(int id) {
        return  announcementDao.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Announcement addAnnouncement(AnnouncementRequest announcementRequest) {
        Optional<Team> optionalTeam = teamDao.findById(announcementRequest.getTeamId());
        if(optionalTeam.isEmpty()) return null;

        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setAnnouncementCreator(announcementRequest.getAnnouncementCreator());
        newAnnouncement.setAnnouncementCreationdate(new Date());
        newAnnouncement.setTeam(optionalTeam.get());
        newAnnouncement.setAnnouncementContent(announcementRequest.getAnnouncementContent());

        return announcementDao.save(newAnnouncement);
    }

    public Announcement editAnnouncement(int id, AnnouncementRequest announcementRequest) {
        // can edit content
        Optional<Announcement> optionalAnnouncement = announcementDao.findById(id);
        if(optionalAnnouncement.isEmpty()) return null;

        Announcement announcementFromDB = optionalAnnouncement.get();
        announcementFromDB.setAnnouncementContent(announcementFromDB.getAnnouncementContent());

        return announcementDao.save(announcementFromDB);
    }
}
