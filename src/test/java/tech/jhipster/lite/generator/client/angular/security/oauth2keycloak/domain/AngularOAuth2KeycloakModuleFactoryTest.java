package tech.jhipster.lite.generator.client.angular.security.oauth2keycloak.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class AngularOAuth2KeycloakModuleFactoryTest {

  private static final AngularOAuth2KeycloakModuleFactory factory = new AngularOAuth2KeycloakModuleFactory();

  @Test
  void shouldBuildModuleWithEmptyAngularFile() {
    assertAngularOAuthModule(emptyAngularJsonFile())
      .hasFile("angular.json")
      .containing("\"allowedCommonJsDependencies\": [\"base64-js\", \"js-sha256\", \"keycloak-js\"]");
  }

  @Test
  void shouldBuildModuleWithAngularFileWithAllowedDependencies() {
    assertAngularOAuthModule(angularJsonFile())
      .hasFile("angular.json")
      .containing("\"allowedCommonJsDependencies\": [\"dummy.js\", \"base64-js\", \"js-sha256\", \"keycloak-js\"]");
  }

  private static JHipsterModuleAsserter assertAngularOAuthModule(ModuleFile angularJson) {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    return assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      environmentFile(),
      prodEnvironmentFile(),
      angularJson,
      appComponentHtmlFile(),
      appComponentFile(),
      appComponentSpecFile(),
      mainFile()
    )
      .hasFile("package.json")
      .containing(nodeDependency("keycloak-js"))
      .and()
      .hasPrefixedFiles(
        "src/main/webapp/app/auth",
        "oauth2-auth.service.ts",
        "oauth2-auth.service.spec.ts",
        "http-auth.interceptor.ts",
        "http-auth.interceptor.spec.ts"
      )
      .hasPrefixedFiles("src/main/webapp/app/login", "login.html", "login.ts", "login.spec.ts")
      .hasFile("src/main/webapp/environments/environment.ts")
      .containing(
        """
          keycloak: {
            url: 'http://localhost:9080',
            realm: 'jhipster',
            client_id: 'web_app',
          },
          production: true,
        """
      )
      .and()
      .hasFile("src/main/webapp/environments/environment.local.ts")
      .containing(
        """
          keycloak: {
            url: 'http://localhost:9080',
            realm: 'jhipster',
            client_id: 'web_app',
          },
        """
      )
      .and()
      .hasFile("src/main/webapp/app/app.html")
      .containing("<jhi-login />")
      .and()
      .hasFile("src/main/webapp/app/app.spec.ts")
      .and();
  }

  private static ModuleFile environmentFile() {
    return file("src/test/resources/projects/angular/environment.local.ts", "src/main/webapp/environments/environment.local.ts");
  }

  private static ModuleFile prodEnvironmentFile() {
    return file("src/test/resources/projects/angular/environment.ts", "src/main/webapp/environments/environment.ts");
  }

  private static ModuleFile angularJsonFile() {
    return file("src/test/resources/projects/angular/angular.json", "angular.json");
  }

  private static ModuleFile emptyAngularJsonFile() {
    return file("src/test/resources/projects/angular/empty-angular.json", "angular.json");
  }

  private static ModuleFile appComponentHtmlFile() {
    return file("src/test/resources/projects/angular/app.html", "src/main/webapp/app/app.html");
  }

  private static ModuleFile appComponentSpecFile() {
    return file("src/test/resources/projects/angular/app.spec.ts", "src/main/webapp/app/app.spec.ts");
  }

  private static ModuleFile appComponentFile() {
    return file("src/test/resources/projects/angular/app.ts", "src/main/webapp/app/app.ts");
  }

  private static ModuleFile mainFile() {
    return file("src/test/resources/projects/angular/main.ts", "src/main/webapp/main.ts");
  }
}
