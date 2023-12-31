package org.mercury.EventService.service;

import lombok.extern.slf4j.Slf4j;
import org.mercury.EventService.bean.*;
import org.mercury.EventService.criteria.SearchCriteria;
import org.mercury.EventService.dao.*;
import org.mercury.EventService.dto.EventCollaborationRequest;
import org.mercury.EventService.dto.EventEditRequest;
import org.mercury.EventService.dto.EventRequest;
import org.mercury.EventService.filter.EventFilter;
import org.mercury.EventService.specification.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

/**
 * @ClassName EventService
 * @Description TODO
 * @Author katefu
 * @Date 8/27/23 7:25 PM
 * @Version 1.0
 **/

@Service
@Slf4j
public class EventService {
    @Autowired
    private EventDao eventDao;

    @Autowired
    private DocumentEventDao documentEventDao;

    @Autowired
    private MeetingEventDao meetingEventDao;

    @Autowired
    private ActivityEventDao activityEventDao;

    private final WebClient webClient;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Autowired
    private EmailService emailService;

    public EventService(){
        this.webClient = WebClient.create("http://localhost:8080/employee/");
    }

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private EventCollaborationDao eventCollaborationDao;

    public Event getById(int id){
        return eventDao.findById(id).orElse(null);
    }

    public List<Event> getByType(String type){
        return eventDao.findEventByEventType(type.toLowerCase());
    }

    public List<Event> getByCreator(int employeeId) {
        return eventDao.findEventByEventCreator(employeeId);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Event addEvent(EventRequest eventRequest) throws IllegalArgumentException {
        Event event = null;

        switch (eventRequest.getEventType().toLowerCase()) {
            case "document":
                event = new DocumentEvent();
                break;
            case "meeting":
                event = new MeetingEvent();
                break;
            case "activity":
                event = new ActivityEvent();
                break;
            default:
                throw new IllegalArgumentException("Invalid event type");
        }

        Optional<Team> teamOptional = teamDao.findById(eventRequest.getEventCreationTeamId());
        if(teamOptional.isEmpty()) {throw new RuntimeException("Event Creation Team Not Exist");}
        else event.setTeam(teamOptional.get());

        event.setEventCreationdate(new Date());
        event.setEventCreator(eventRequest.getEventCreator());
        event.setEventType(eventRequest.getEventType());
        event.setEventTitle(eventRequest.getEventTitle());
        event.setEventDescription(eventRequest.getEventDescription());
        event.setEventExpired(false);
        event.setEventLastUpdatedate(new Date());

        // Set event-specific fields
        if (event instanceof DocumentEvent documentEvent) {
            documentEvent.setDocumentLink(eventRequest.getDocumentLink());
            documentEvent.setDeadline(eventRequest.getDeadline());
            documentEventDao.save(documentEvent);
        } else if (event instanceof MeetingEvent meetingEvent) {
            meetingEvent.setMeetingLocation(eventRequest.getMeetingLocation());
            meetingEvent.setMeetingVirtual(eventRequest.isMeetingVirtual());
            meetingEvent.setMeetingStarttime(eventRequest.getMeetingStarttime());
            meetingEvent.setMeetingEndtime(eventRequest.getMeetingEndtime());
            meetingEvent.setMeetingNoteLink(eventRequest.getMeetingNoteLink());
            meetingEvent.setMeetingAgendaLink(eventRequest.getMeetingAgendaLink());
            meetingEvent.setMeetingType(eventRequest.getMeetingType());
            meetingEventDao.save(meetingEvent);
        } else {
            ActivityEvent activityEvent = (ActivityEvent) event;
            activityEvent.setActivityVirtual(eventRequest.isActivityVirtual());
            activityEvent.setActivityLocation(eventRequest.getActivityLocation());
            activityEvent.setActivityStarttime(eventRequest.getActivityStarttime());
            activityEvent.setActivityEndtime(eventRequest.getActivityEndtime());
            activityEventDao.save(activityEvent);
        }

        Event savedEvent = eventDao.save(event);

        List<EventCollaborationRequest> collaborationRequests = eventRequest.getCollaborations();
        if (collaborationRequests != null && !collaborationRequests.isEmpty()) {
            for (EventCollaborationRequest collaborationRequest : collaborationRequests) {
                Optional<Team> invitedTeamOptional = teamDao.findById(collaborationRequest.getTeamId());
                if (invitedTeamOptional.isPresent()) {
                    Team invitedTeam = invitedTeamOptional.get();

                    //TODO: team's creator and supervisor will receive an email
                    List<TeamMember> teamMembers = teamMemberDao.findAllByTeamTeamId(invitedTeam.getTeamId());
                    List<Employee> employees = new ArrayList<>();

                    for(TeamMember member: teamMembers){
                        if(member.getRole().equals("owner") || member.getRole().equals("supervisor")){
                            Employee employee = webClient.get()
                                    .uri("/{id}", member.getEmployeeId())
                                    .retrieve()
                                    .bodyToMono(Employee.class)
                                    .block(); // This makes the call synchronous
                            if (employee != null) {
                                employees.add(employee);
                            }
                        }

                    }

                    inviteEventMember(invitedTeam.getTeamName(), savedEvent.getEventTitle(), employees);


                    EventCollaboration eventCollaboration = new EventCollaboration();
                    eventCollaboration.setEvent(savedEvent);
                    eventCollaboration.setTeam(invitedTeam);
                    eventCollaboration.setInvitedate(new Date());
                    eventCollaboration.setAcceptStatus(false); // Initial status
                    eventCollaboration.setTeamRole(collaborationRequest.getTeamRole());

                    eventCollaborationDao.save(eventCollaboration);
                } else {
                    throw new IllegalArgumentException("Collaboration Team id "+ collaborationRequest.getTeamId() + " not found");
                }
            }
        }

        return savedEvent;
    }

    private void inviteEventMember(String teamName, String eventName, List<Employee> members){
        for(Employee member: members){
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", member.getEmployeeFirstname() + " " + member.getEmployeeLastname());
            placeholders.put("team_name", teamName);
            placeholders.put("event_name", eventName);
            placeholders.put("action_url", "http://localhost:5174/user/dashboard");
            placeholders.put("support_email", "yuehaofu207@gmail.com");
            emailService.sendEmail(
                    "invite",
                    member.getEmployeeEmail(),
                    "CollaSpace | Event Collaboration Invitation",
                    placeholders);
        }
    }

    public Event editEvent(int id, EventEditRequest editRequest) {
        Event existingEvent = eventDao.findById(id).orElse(null);
        if (existingEvent == null) {
            throw new IllegalArgumentException("Event not found");
        }

        existingEvent.setEventDescription(editRequest.getEventDescription());
        existingEvent.setEventLastUpdatedate(new Date());

        if (existingEvent instanceof DocumentEvent documentEvent) {
            documentEvent.setDocumentLink(editRequest.getDocumentLink());
            documentEvent.setDeadline(editRequest.getDeadline());
            documentEventDao.save(documentEvent);
        } else if (existingEvent instanceof MeetingEvent meetingEvent) {
            meetingEvent.setMeetingLocation(editRequest.getMeetingLocation());
            meetingEvent.setMeetingVirtual(editRequest.isMeetingVirtual());
            meetingEvent.setMeetingNoteLink(editRequest.getMeetingNoteLink());
            meetingEvent.setMeetingAgendaLink(editRequest.getMeetingAgendaLink());
            meetingEvent.setMeetingType(editRequest.getMeetingType());
            meetingEventDao.save(meetingEvent);
        } else if (existingEvent instanceof ActivityEvent activityEvent) {
            activityEvent.setActivityVirtual(editRequest.isActivityVirtual());
            activityEvent.setActivityLocation(editRequest.getActivityLocation());
            activityEventDao.save(activityEvent);
        }

        return eventDao.save(existingEvent);
    }

    public List<Event> getWithFilter(EventFilter eventFilter){

        // general;
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        if (eventFilter.getEventTitle() != null) {
            searchCriteriaList.add(new SearchCriteria("eventTitle", ":", eventFilter.getEventTitle()));
        }
        if (eventFilter.getEventDescription() != null) {
            searchCriteriaList.add(new SearchCriteria("eventDescription", ":", eventFilter.getEventDescription()));
        }

        if(eventFilter.getEventExpired()!=null){
            searchCriteriaList.add(new SearchCriteria("eventExpired", ":", eventFilter.getEventExpired()));
        }

        if(eventFilter.getEventCreationdateStart()!=null){
            searchCriteriaList.add(new SearchCriteria("eventCreationdate", ">", eventFilter.getEventCreationdateStart()));
        }

        if(eventFilter.getEventCreationdateEnd()!=null){
            searchCriteriaList.add(new SearchCriteria("eventCreationdate", "<", eventFilter.getEventCreationdateEnd()));
        }

        if(eventFilter.getEventLastUpdatedateStart()!=null){
            searchCriteriaList.add(new SearchCriteria("eventLastUpdatedate", ">", eventFilter.getEventLastUpdatedateStart()));
        }

        if(eventFilter.getEventLastUpdatedateEnd()!=null){
            searchCriteriaList.add(new SearchCriteria("eventLastUpdatedate", "<", eventFilter.getEventLastUpdatedateEnd()));
        }

        String eventType = eventFilter.getEventType();
        if(eventType!=null){
            switch (eventType.toLowerCase()) {
                case "document" -> {
                    if (eventFilter.getDeadlineStart() != null) {
                        searchCriteriaList.add(new SearchCriteria("deadline", ">", eventFilter.getDeadlineStart()));
                    }
                    if (eventFilter.getDeadlineEnd() != null) {
                        searchCriteriaList.add(new SearchCriteria("deadline", "<", eventFilter.getDeadlineEnd()));
                    }
                }
                case "meeting" -> {
                    if (eventFilter.getMeetingVirtual() != null) {
                        searchCriteriaList.add(new SearchCriteria("meetingVirtual", ":", eventFilter.getMeetingVirtual()));
                    }
                    if (eventFilter.getMeetingLocation() != null) {
                        searchCriteriaList.add(new SearchCriteria("meetingLocation", ":", eventFilter.getMeetingLocation()));
                    }
                    if (eventFilter.getMeetingType() != null) {
                        searchCriteriaList.add(new SearchCriteria("meetingType", ":", eventFilter.getMeetingType()));
                    }
                    if (eventFilter.getMeetingStarttimeStart() != null) {
                        searchCriteriaList.add(new SearchCriteria("meetingStarttime", ">", eventFilter.getMeetingStarttimeStart()));
                    }
                    if (eventFilter.getMeetingStarttimeEnd() != null) {
                        searchCriteriaList.add(new SearchCriteria("meetingStarttime", "<", eventFilter.getMeetingStarttimeEnd()));
                    }
                }
                case "activity" -> {
                    if (eventFilter.getActivityVirtual() != null) {
                        searchCriteriaList.add(new SearchCriteria("activityVirtual", ":", eventFilter.getActivityVirtual()));
                    }
                    if (eventFilter.getActivityLocation() != null) {
                        searchCriteriaList.add(new SearchCriteria("activityLocation", ":", eventFilter.getActivityLocation()));
                    }
                    if (eventFilter.getActivityStarttimeStart() != null) {
                        searchCriteriaList.add(new SearchCriteria("activityStarttime", ">", eventFilter.getActivityStarttimeStart()));
                    }
                    if (eventFilter.getActivityStarttimeEnd() != null) {
                        searchCriteriaList.add(new SearchCriteria("activityStarttime", "<", eventFilter.getActivityStarttimeEnd()));
                    }
                }
            }
        }


        List<EventSpecification> specifications = searchCriteriaList.stream()
                .map(EventSpecification::new).toList();

        Specification<Event> finalSpecification = Specification.where(specifications.get(0));
        for (int i = 1; i < specifications.size(); i++) {
            finalSpecification = finalSpecification.and(specifications.get(i));
        }

        return eventDao.findAll(finalSpecification);
    }

    public List<Event> getByCreationTeamId(int teamId) {
        Optional<Team> teamOptional = teamDao.findById(teamId);
        if(teamOptional.isEmpty()) {return null;}
        else return eventDao.findEventByTeam(teamOptional.get());
    }

    public List<Event> getByTeamId(int teamId) {
        // get team events: created by this team, involve this team
        List<Event> allEvents;
        Team team = teamDao.findById(teamId).orElse(null);
        if(team==null) {return null;}
        else allEvents = new ArrayList<>(eventDao.findEventByTeam(team));

        List<EventCollaboration> collaborations = eventCollaborationDao.findAllByTeam(team);
        for(EventCollaboration c: collaborations){
            allEvents.add(c.getEvent());
        }

        return allEvents;
    }
}
