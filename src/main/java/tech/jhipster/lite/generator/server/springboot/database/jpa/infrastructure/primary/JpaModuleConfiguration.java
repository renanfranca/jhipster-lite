package tech.jhipster.lite.generator.server.springboot.database.jpa.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JPA_MARIADB;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JPA_MSSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JPA_MYSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JPA_POSTGRESQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.jpa.application.JpaApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class JpaModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  JHipsterModuleResource jpaPostgresqlModule(JpaApplicationService postgresql) {
    return JHipsterModuleResource.builder()
      .slug(JPA_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with Postgresql to project")
      .organization(organization())
      .tags(tags())
      .factory(postgresql::buildPostgresql);
  }

  @Bean
  JHipsterModuleResource jpaMariaDBModule(JpaApplicationService applicationService) {
    return JHipsterModuleResource.builder()
      .slug(JPA_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MariaDB to project")
      .organization(organization())
      .tags(tags())
      .factory(applicationService::buildMariaDB);
  }

  @Bean
  JHipsterModuleResource jpaMmySQLModule(JpaApplicationService applicationService) {
    return JHipsterModuleResource.builder()
      .slug(JPA_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MySQL to project")
      .organization(organization())
      .tags(tags())
      .factory(applicationService::buildMySQL);
  }

  @Bean
  JHipsterModuleResource jpaMsSQLModule(JpaApplicationService applicationService) {
    return JHipsterModuleResource.builder()
      .slug(JPA_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MsSQL to project")
      .organization(organization())
      .tags(tags())
      .factory(applicationService::buildMsSQL);
  }

  private static JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder()
      .addBasePackage()
      .addIndentation()
      .addProjectBaseName()
      .addSpringConfigurationFormat()
      .build();
  }

  private static JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(SPRING_BOOT).build();
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "database" };
  }
}
