# Error log
## Environment Setup

- child projects cannot find dependencies
  - put dependencies in parent pom, not only in DependencyManagement
  - add parent to child's pom dependencies
  ```xml
    <dependencies>
      <dependency>
      <groupId>org.mercury</groupId>
      <artifactId>collaspace-parent</artifactId>
      <version>1.0-SNAPSHOT</version>
      <type>pom</type>
      <scope>import</scope>
      </dependency>
      </dependencies>
  ```
- Null value was assigned to a property of primitive type setter
  - use Integer instead of int
## Database Service
- customized filter with key, operation, value
  - JPA Specification
    - xxxSpecification implements Specification
    - xxxDao implements JpaSpecificationExecutor
    - SearchCriteria
  - multiple conditions
    - first where, following and, lastly findAll
  - cannot parse date
    - if getValue() is string, parse date: 
      SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    dateFormat.parse(dateStr);
    - if getValue() is Date, just (Date)getValue()

## MVC
- Ticket and TicketAssign(contains ticket, and other fields, many-to-one relationship with Ticket), when save a ticket and its multiple TicketAssigns
  - Q: do I need to separately save for ticketAssign?
    - Q: if not, how to send request (yes, send as list of TicketAssign)
    - Q: if yes, how to save ('mappedBy' a property named 'ticketId' which does not exist in the target entity 'org.mercury.TicketService.bean.TicketAssign')
- column t1_0.ticket_ticket_id does not exist
  - the relationship table's FK must be {reference_table_name}_{column_name}.
- if I add @JsonIgnore in relationship bean's getter for reference object, how can I get output that object id?
  - create a new class for 'TicketLogDTO'
  ```java
  public class TicketLogDTO {
    private int ticketLogId;
    private int ticketId;
    private int ticketLogCreator;
    private Date ticketLogCreationdate;
    private String ticketLogContent;

    public TicketLogDTO(TicketLog ticketLog) {
        this.ticketLogId = ticketLog.getTicketLogId();
        this.ticketId = ticketLog.getTicket().getTicketId();
        this.ticketLogCreator = ticketLog.getTicketLogCreator();
        this.ticketLogCreationdate = ticketLog.getTicketLogCreationdate();
        this.ticketLogContent = ticketLog.getTicketLogContent();
    }
  }
  ```
  - In controller, convert TicketLog from service to TicketLogDTO
  ```java
    new TicketLogDTO(ticketLog);
  ```

- When I need to add row in the relationship bean (TicketLog), which contains (Ticket) how can I pass through request?
  - create a new class for 'TicketLogCreateRequest' with only needed fields
- In Specification class: Could not resolve attribute 'eventCreationdateStart' of 'org.mercury.EventService.bean.Event'
  - Key in SearchCriteria should be 'eventCreationdate'