package org.mercury.TicketService.service;

import org.mercury.TicketService.bean.Employee;
import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.bean.TicketAssign;
import org.mercury.TicketService.bean.TicketLog;
import org.mercury.TicketService.criteria.SearchCriteria;
import org.mercury.TicketService.dao.TicketAssignDao;
import org.mercury.TicketService.dao.TicketDao;
import org.mercury.TicketService.dto.DocumentCreationRequest;
import org.mercury.TicketService.dto.TicketCreationRequest;
import org.mercury.TicketService.dto.TicketEditRequest;
import org.mercury.TicketService.filter.TicketFilter;
import org.mercury.TicketService.http.Response;
import org.mercury.TicketService.specification.TicketSpecification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

/**
 * @ClassName TicketService
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 9:48 AM
 * @Version 1.0
 **/

@Service
public class TicketService {
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private TicketAssignDao ticketAssignDao;

    @Autowired
    private EmailService emailService;

    private final WebClient webClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public TicketService(){
        this.webClient = WebClient.create("http://localhost:8080/employee/");
    }

    public Ticket getById(int id){
        Optional<Ticket> optionalTicket = ticketDao.findById(id);
        return optionalTicket.orElse(null);
    }

    public List<Ticket> getByCreatorId(int id){
        return ticketDao.findByTicketCreator(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Response addTicket(TicketCreationRequest request){
        Ticket ticket = new Ticket();
        ticket.setTicketCreator(request.getTicketCreator());
        ticket.setTicketCreationdate(new Date());
        ticket.setTicketLastUpdatedate(new Date());
        ticket.setTicketStatus("pending");
        ticket.setTicketTitle(request.getTicketTitle());
        ticket.setTicketDescription(request.getTicketDescription());
        ticket.setTicketPriority(request.getTicketPriority());
        ticket.setTicketFromTeam(request.getTicketFromTeam());
        ticket.setTicketDuedate(request.getTicketDueDate());
        Ticket newTicket = ticketDao.save(ticket);

        List<TicketAssign> assigns = new ArrayList<>();

        TicketAssign assignee = new TicketAssign();
        assignee.setTicket(newTicket);
        assignee.setEmployeeId(request.getAssigneeId());
        assignee.setRole("assignee");
        assignee.setTicketAssigndate(new Date());
        assigns.add(assignee);
        ticketAssignDao.save(assignee);

        if(request.getViewerIds()!= null && request.getViewerIds().size()>0){
            request.getViewerIds().forEach((viewerId -> {
                TicketAssign viewer = new TicketAssign();
                viewer.setTicket(newTicket);
                viewer.setEmployeeId(viewerId);
                viewer.setRole("viewer");
                viewer.setTicketAssigndate(new Date());
                assigns.add(viewer);
                ticketAssignDao.save(viewer);
            }));
        }

        if(request.getSupervisorIds()!=null && request.getSupervisorIds().size()>0){
            request.getSupervisorIds().forEach((supervisorId -> {
                TicketAssign supervisor = new TicketAssign();
                supervisor.setTicket(newTicket);
                supervisor.setEmployeeId(supervisorId);
                supervisor.setRole("supervisor");
                supervisor.setTicketAssigndate(new Date());
                assigns.add(supervisor);
                ticketAssignDao.save(supervisor);
            }));
        }


        newTicket.setAssigns(assigns);

        if(request.getFiles()!=null && request.getFiles().size()>0){
            List<DocumentCreationRequest> documentRequests = new ArrayList<>();
            request.getFiles().forEach(file -> {
                DocumentCreationRequest documentRequest = new DocumentCreationRequest(file, "ticket");
                documentRequests.add(documentRequest);
            });
            rabbitTemplate.convertAndSend("", "q.create-document", documentRequests);
        }

        try{
            List<TicketAssign> assignRoles = newTicket.getAssigns();
            List<Employee> employees = new ArrayList<>();
            assignRoles.forEach((ticketAssign -> {
                Employee employee = webClient.get()
                        .uri("/{id}",ticketAssign.getEmployeeId())
                        .retrieve()
                        .bodyToMono(Employee.class)
                        .block(); // This makes the call synchronous
                if (employee != null) {
                    employees.add(employee);
                }
            }));

            Employee creator = webClient.get()
                    .uri("/{id}", request.getTicketCreator())
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();

            assert creator != null;

            inviteTicketMember(request.getTicketTitle(), creator.getEmployeeFirstname() + " "+ creator.getEmployeeLastname(), employees);

            // ticketlogs is null when passed in
            // is a log for creation necessary?
            List<TicketLog> logs = new ArrayList<>();
            logs.add(new TicketLog(ticket, ticket.getTicketCreator(), new Date(), "Ticket Created"));
            ticket.setTicketLogs(logs);

            return new Response(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Response(false);
        }


    }

    private void inviteTicketMember(String ticketName, String creatorName, List<Employee> members){
        for(Employee member: members){
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", member.getEmployeeFirstname() + " " + member.getEmployeeLastname());
            placeholders.put("ticket_name", ticketName);
            placeholders.put("creator_name", creatorName);

            placeholders.put("action_url", "http://localhost:5174/user/dashboard");
            placeholders.put("support_email", "yuehaofu207@gmail.com");
            emailService.sendEmail(
                    "invite",
                    member.getEmployeeEmail(),
                    "CollaSpace | Ticket Assign",
                    placeholders);
        }
    }

    public List<Ticket> getWithFilter(TicketFilter ticketFilter){
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        String ticketCreator = ticketFilter.getTicketCreator();
        if(!ticketCreator.isEmpty()){
            searchCriteriaList.add(new SearchCriteria("ticketCreator", ":", ticketCreator));
        }
        Date ticketCreationdateStart = ticketFilter.getTicketCreationdateStart();
        if(ticketCreationdateStart!=null){
            searchCriteriaList.add(new SearchCriteria("ticketCreationdateStart", ">", ticketCreationdateStart));
        }

        Date ticketCreationdateEnd = ticketFilter.getTicketCreationdateEnd();
        if(ticketCreationdateEnd!=null){
            searchCriteriaList.add(new SearchCriteria("ticketCreationdateEnd", "<", ticketCreationdateEnd));
        }

        Date ticketLastUpdatedateStart = ticketFilter.getTicketLastUpdatedateStart();
        if(ticketLastUpdatedateStart!=null){
            searchCriteriaList.add(new SearchCriteria("ticketLastUpdatedateStart", ">", ticketLastUpdatedateStart));
        }

        Date ticketLastUpdatedateEnd = ticketFilter.getTicketLastUpdatedateEnd();
        if(ticketLastUpdatedateEnd!=null){
            searchCriteriaList.add(new SearchCriteria("ticketLastUpdatedateEnd", ">", ticketLastUpdatedateEnd));
        }

        String ticketTitle = ticketFilter.getTicketTitle();
        if(!ticketTitle.isEmpty()){
            searchCriteriaList.add(new SearchCriteria("ticketTitle", ":", ticketTitle));
        }
        String ticketStatus = ticketFilter.getTicketTitle();
        if(!ticketStatus.isEmpty()){
            searchCriteriaList.add(new SearchCriteria("ticketStatus", ":", ticketStatus));
        }

        List<TicketSpecification> specifications = searchCriteriaList.stream()
                .map(TicketSpecification::new).toList();

        Specification<Ticket> finalSpecification = Specification.where(specifications.get(0));
        for (int i = 1; i < specifications.size(); i++) {
            finalSpecification = finalSpecification.and(specifications.get(i));
        }

        return ticketDao.findAll(finalSpecification);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Response editTicket(TicketEditRequest request){
        // can only edit ticket's status, description
        try{
            Ticket ticketFromDB = ticketDao.findById(request.getTicketId()).orElse(null);
            if(ticketFromDB==null){
                return new Response(false);
            }
            ticketFromDB.setTicketDescription(request.getTicketDescription());
            ticketFromDB.setTicketStatus(request.getTicketStatus());
            ticketFromDB.setTicketLastUpdatedate(new Date());
            Ticket editedTicket = ticketDao.save(ticketFromDB);

            List<TicketAssign> assigns = new ArrayList<>();
            List<TicketAssign> oldAssigns = editedTicket.getAssigns();

            List<Integer> oldAssignIds = getAssignIds(oldAssigns);
            List<Integer> newAssignIds = new ArrayList<>();
            if(request.getAddViewerIds()!=null && request.getAddViewerIds().size()>0){
                request.getAddViewerIds().forEach((viewerId -> {
                    if(!oldAssignIds.contains(viewerId)){
                        TicketAssign viewer = new TicketAssign();
                        viewer.setTicket(editedTicket);
                        viewer.setEmployeeId(viewerId);
                        viewer.setRole("viewer");
                        viewer.setTicketAssigndate(new Date());
                        newAssignIds.add(viewerId);
                        assigns.add(viewer);
                        ticketAssignDao.save(viewer);
                    }

                }));
            }

            if(request.getAddSupervisorIds()!=null && request.getAddSupervisorIds().size()>0){
                request.getAddSupervisorIds().forEach((supervisorId -> {
                    if(!oldAssignIds.contains(supervisorId)){
                        TicketAssign supervisor = new TicketAssign();
                        supervisor.setTicket(editedTicket);
                        supervisor.setEmployeeId(supervisorId);
                        supervisor.setRole("supervisor");
                        supervisor.setTicketAssigndate(new Date());
                        newAssignIds.add(supervisorId);
                        assigns.add(supervisor);
                        ticketAssignDao.save(supervisor);
                    }

                }));
            }

            oldAssigns.addAll(assigns);

                List<Employee> employees = new ArrayList<>();
                newAssignIds.forEach((employeeId -> {
                    Employee employee = webClient.get()
                            .uri("/{id}",employeeId)
                            .retrieve()
                            .bodyToMono(Employee.class)
                            .block(); // This makes the call synchronous
                    if (employee != null) {
                        employees.add(employee);
                    }
                }));

                Employee creator = webClient.get()
                        .uri("/{id}", editedTicket.getTicketCreator())
                        .retrieve()
                        .bodyToMono(Employee.class)
                        .block();

                assert creator != null;

                inviteTicketMember(editedTicket.getTicketTitle(), creator.getEmployeeFirstname() + " "+ creator.getEmployeeLastname(), employees);
                return new Response(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Response(false);
        }
    }

    private List<Integer> getAssignIds(List<TicketAssign> oldAssigns) {
        List<Integer> ids = new ArrayList<>();
        oldAssigns.forEach((ticketAssign -> {
            ids.add(ticketAssign.getEmployeeId());
        }));
        return ids;
    }

    public List<Ticket> getByEmployeeId(int id) {
        // all tickets: created by, assigned to
        List<Ticket> allTickets;
        allTickets = new ArrayList<>(ticketDao.findByTicketCreator(id));
        List<TicketAssign> ticketAssigns = ticketAssignDao.findByEmployeeId(id);
        if(ticketAssigns== null || ticketAssigns.isEmpty()) return allTickets;
        for(TicketAssign ticketAssign : ticketAssigns){
            allTickets.add( ticketAssign.getTicket());
        }

        return allTickets;

    }

    public List<Ticket> getByTeamId(int teamId) {
        List<Ticket> allTickets;
        allTickets = new ArrayList<>(ticketDao.findByTicketFromTeam(teamId));
        List<TicketAssign> ticketAssigns = ticketAssignDao.findByTeamId(teamId);
        if(ticketAssigns== null || ticketAssigns.isEmpty()) return allTickets;
        for(TicketAssign ticketAssign : ticketAssigns){
            allTickets.add( ticketAssign.getTicket());
        }

        return allTickets;
    }
}
