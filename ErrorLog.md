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