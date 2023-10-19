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
- table id keeps saying duplicate
  - remove subclass's id field
## Third Party Dependencies
### Swagger 
 - Swagger from SpringFox not works with Springboot3, need to use openAPI
```xml
            <dependency>
                <groupId>org.springdoc</groupId>
                <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
                <version>2.2.0</version>
            </dependency>
```
 - Add @OpenAPIDefinition in main class
 - Problem: No operations defined in spec:
   - Swagger Config class: 
   ```java
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("collaspace-public")
                .pathsToMatch("/account/**")
                .build();
    }
    ```
   - application.properties
   ```xml
    springdoc.packagesToScan=org.mercury.AccountService
    springdoc.swagger-ui.display-request-duration=true
    springdoc.swagger-ui.use-root-path=true
    springdoc.swagger-ui.disable-swagger-default-url=true
    ```
 - entry url: http://localhost:8081/swagger-ui/index.html?urls.primaryName=collaspace-public
 - Deploy Swagger on Microservice, ApiGateway
   - ApiGate way dependency should be webflux
### Eureka
- Tomcat unable to start
  - remove spring security related dependencies from parent
- @EnableEurekaClient import not found
  - use @EnableDiscoveryClient or just have the pom dependency
- Multiple instances for one service
  - set server-port to 0
  - call as 'http://inventory-service/api/inventory'
  - enable client side load balancing
  - OrderService's web client config 
    - WebClient -> WebClient.Builder
    - add @LoadBalanced
### Api Gateway
  - Spring MVC found on classpath, which is incompatible with Spring Cloud Gateway.
    - Add to application.properties: spring.main.web-application-type=reactive
    - Request path 404
      - Add to ApiGatewayApplication:
      ```java
      @Bean
      public HttpClient httpClient() {
          return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
      }
      ```

### Spring Security
- SecurityFilterChain methods deprecated: csrf, requestMatchers ...
```java
public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/token/**").permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        .httpBasic(Customizer.withDefaults())
        .build();
}
```
- JWT DefaultJwtBuilder class not found
  - add other dependencies:
  ```xml
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
  ```
  
### Microservice Communication
- Feigh VS webclient: 
  - The WebClient is Asynchronous and non blocking REST invoker. While the Feign client and RestTemplates creates a thread for each request and blocks it until it receives a response.
- RabbitMQ
    - add queue:
      - add in management console: http://localhost:15672/#/
      - add in RabbitMQ Config file:
      ```java
      @Configuration
      public class RabbitMQConfig {
          @Bean
          public Queue createUserRegistrationQueue() {
              return new Queue("q.user-registration");
          }
      }
      ```
    - send message:
  ```java
    rabbitTemplate.convertAndSend("", "get-employee-teams", teamsRequest);
    ```
  Here first argument is exchange, second one is routing key and last one is the payload.
  In the rabbitMQ universe, an exchange is like a post office, a queue is like the physical location, and the routing key is the address of that location. When we created the queue and didn’t bound with any exchange, the default exchange automatically got bound with it and took the routing key as the queue’s name.
    - The request object should implement Serializable interface
 - How to continue logic after receiving all messages?
   - completable future
 - if two request for company coming too close, it will receive the same company
## Frontend Connection
- CORS enable: enable in actual service
```java
      @Bean
      public WebMvcConfigurer corsConfigurer() {
          return new WebMvcConfigurer() {
              @Override
              public void addCorsMappings(CorsRegistry registry) {
                  registry.addMapping("/**").allowedOrigins("http://localhost:5174");
              }
          };
      }
   ```
- Can get but post still not allowed