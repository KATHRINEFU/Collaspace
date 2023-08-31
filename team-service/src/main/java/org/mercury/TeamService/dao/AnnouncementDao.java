package org.mercury.TeamService.dao;

import org.mercury.TeamService.bean.Announcement;
import org.mercury.TeamService.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementDao extends JpaRepository<Announcement, Integer> {
    List<Announcement> findAllByTeam(Team team);
}
