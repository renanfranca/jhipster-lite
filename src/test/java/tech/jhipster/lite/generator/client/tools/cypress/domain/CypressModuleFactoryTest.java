package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class CypressModuleFactoryTest {

  private static final CypressModuleFactory factory = new CypressModuleFactory();

  @Test
  void shouldBuildModuleOnProjectWithoutTsConfig() {
    assertCypressModule(packageJsonFile());
  }

  @Test
  void shouldBuildModuleOnProjectWithEmptyTsConfig() {
    assertCypressModule(packageJsonFile(), file("src/test/resources/projects/empty-ts-config/tsconfig.json", "tsconfig.json"))
      .hasFile("tsconfig.json")
      .containing(
        """
          },
          "exclude": ["src/test/webapp/component/**/*.ts"]
        }\
        """
      );
  }

  @Test
  void shouldBuildModuleOnProjectWithTsConfigWithEmptyExclusions() {
    assertCypressModule(
      packageJsonFile(),
      file("src/test/resources/projects/ts-config-with-empty-exclusions/tsconfig.json", "tsconfig.json")
    )
      .hasFile("tsconfig.json")
      .containing("\"exclude\": [\"src/test/webapp/component/**/*.ts\"]");
  }

  @Test
  void shouldBuildModuleOnProjectWithTsConfigWithExistingExclusions() {
    assertCypressModule(packageJsonFile(), file("src/test/resources/projects/ts-config-with-exclusions/tsconfig.json", "tsconfig.json"))
      .hasFile("tsconfig.json")
      .containing("  \"exclude\": [\"src/test/webapp/component/**/*spec.ts\", \"node_modules\", \"src/test/webapp/component/**/*.ts\"]");
  }

  @Test
  void shouldNotDuplicateCypressExclusion() {
    assertCypressModule(
      packageJsonFile(),
      file("src/test/resources/projects/ts-config-with-cypress-exclusions/tsconfig.json", "tsconfig.json")
    )
      .hasFile("tsconfig.json")
      .containing("  \"exclude\": [\"src/test/webapp/component/**/*spec.ts\", \"node_modules\", \"src/test/webapp/component/**/*.ts\"]");
  }

  private static JHipsterModuleAsserter assertCypressModule(ModuleFile... files) {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    return assertThatModuleWithFiles(module, files)
      .hasFile("package.json")
      .containing(nodeDependency("cypress"))
      .containing(nodeDependency("eslint-plugin-cypress"))
      .containing(nodeScript("e2e", "npm run test:component"))
      .containing(nodeScript("e2e:headless", "npm run test:component:headless"))
      .containing(
        nodeScript(
          "test:component",
          "start-server-and-test start http://localhost:9000 'cypress open --config-file src/test/webapp/component/cypress-config.ts'"
        )
      )
      .containing(
        nodeScript(
          "test:component:headless",
          "start-server-and-test start http://localhost:9000 'cypress run --headless --config-file src/test/webapp/component/cypress-config.ts'"
        )
      )
      .and()
      .hasFile("src/test/webapp/component/cypress-config.ts")
      .containing("baseUrl: 'http://localhost:9000',")
      .and()
      .hasPrefixedFiles("src/test/webapp/component", ".eslintrc.cjs", "tsconfig.json")
      .hasFiles("src/test/webapp/component/common/primary/app/Home.spec.ts")
      .hasPrefixedFiles("src/test/webapp/component/utils", "Interceptor.ts", "DataSelector.ts");
  }
}
