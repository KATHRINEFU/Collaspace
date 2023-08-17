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
  