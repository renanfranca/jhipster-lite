package tech.jhipster.lite.generator.server.springboot.localeprofile.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.buildProfileActivation;
import static tech.jhipster.lite.module.domain.JHipsterModule.buildPropertyKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.buildPropertyValue;
import static tech.jhipster.lite.module.domain.JHipsterModule.mavenPlugin;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.path;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyValue;
import static tech.jhipster.lite.module.domain.JHipsterModule.text;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.buildproperties.PropertyKey;
import tech.jhipster.lite.module.domain.buildproperties.PropertyValue;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class LocalProfileModuleFactory {

  private static final String DELIMITER = "@";
  private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaBuildProperties()
        .set(new PropertyKey(SPRING_PROFILES_ACTIVE), new PropertyValue(""))
        .and()
      .gradleConfigurations()
        .addConfiguration(
          """
          tasks.build {
            dependsOn("processResources")
          }

          tasks.processResources {
            filesMatching("**/*.yml") {
              filter { it.replace("@spring.profiles.active@", springProfilesActive) }
            }
            filesMatching("**/*.properties") {
              filter { it.replace("@spring.profiles.active@", springProfilesActive) }
            }
          }
          """
        )
        .and()
      .javaBuildProfiles()
        .addProfile("local")
          .activation(buildProfileActivation().activeByDefault())
          .properties()
            .set(springActiveProfileProperty(), buildPropertyValue("local"))
            .and()
          .and()
        .and()
      .mavenPlugins()
        .pluginManagement(resourcesPlugin())
        .and()
      .springMainProperties()
        .set(propertyKey(SPRING_PROFILES_ACTIVE), propertyValue(DELIMITER + springActiveProfileProperty().get() + DELIMITER))
        .and()
      .optionalReplacements()
        .in(path(".github/workflows/github-actions.yml"))
          .add(text("./mvnw clean verify"), "./mvnw clean verify -P'!local'")
          .add(text("./gradlew clean integrationTest --no-daemon"), "./gradlew clean integrationTest -Pprofile=local --no-daemon")
          .and()
        .in(path(".gitlab-ci.yml"))
          .add(text("./mvnw clean verify"), "./mvnw clean verify -P'!local'")
      .add(text("./gradlew clean integrationTest $GRADLE_CLI_OPTS"), "./gradlew clean integrationTest -Pprofile=local $GRADLE_CLI_OPTS")
          .and()
        .and()
      .build();
    // @formatter:on
  }

  private static PropertyKey springActiveProfileProperty() {
    return buildPropertyKey(SPRING_PROFILES_ACTIVE);
  }

  private MavenPlugin resourcesPlugin() {
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-resources-plugin")
      .versionSlug("maven-resources-plugin")
      .configuration(
        """
        <useDefaultDelimiters>false</useDefaultDelimiters>
        <delimiters>
          <delimiter>%s</delimiter>
        </delimiters>
        """.formatted(DELIMITER)
      )
      .build();
  }
}
