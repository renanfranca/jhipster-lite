Feature: Jooq PostgreSQL module

  Scenario: Should add postgreSQL elements using legacy url
    When I apply modules to default project
      | maven-java      |
      | spring-boot     |
      | jooq-postgresql |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | postgresql.md |
    And I should have files in "src/main/docker"
      | postgresql.yml |
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |

  Scenario: Should get postgreSQL module properties definition
    When I get module "jooq-postgresql" properties definition
    Then I should have properties definitions
      | Key                       | Type    | Mandatory |
      | packageName               | STRING  | true      |
      | baseName                  | STRING  | true      |
      | indentSize                | INTEGER | false     |
      | springConfigurationFormat | STRING  | false     |

  Scenario: Should add postgreSQL elements using module url
    When I apply "jooq-postgresql" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | postgresql.md |
    And I should have files in "src/main/docker"
      | postgresql.yml |
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |
