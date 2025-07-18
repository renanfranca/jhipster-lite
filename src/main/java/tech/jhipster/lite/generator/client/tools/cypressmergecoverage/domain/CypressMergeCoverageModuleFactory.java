package tech.jhipster.lite.generator.client.tools.cypressmergecoverage.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.fileStart;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.lineAfterRegex;
import static tech.jhipster.lite.module.domain.JHipsterModule.packageName;
import static tech.jhipster.lite.module.domain.JHipsterModule.path;
import static tech.jhipster.lite.module.domain.JHipsterModule.regex;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptCommand;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.text;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;
import static tech.jhipster.lite.module.domain.nodejs.JHLiteNodePackagesVersionSource.COMMON;

import java.util.function.Consumer;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class CypressMergeCoverageModuleFactory {

  private static final JHipsterSource SOURCE = from("client/tools/cypressmergecoverage");
  private static final String CYPRESS_COMPONENT_TESTS = "src/test/webapp/component";

  public JHipsterModule buildCypressMergeCoverage(JHipsterModuleProperties properties) {
    // @formatter:off
    return JHipsterModule.moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("@cypress/code-coverage"), COMMON)
        .addDependency(packageName("cpy-cli"), COMMON)
        .addDependency(packageName("rimraf"), COMMON)
        .addDependency(packageName("vite-plugin-istanbul"), COMMON)
        .addDependency(packageName("nyc"), COMMON)
        .addScript(
          scriptKey("test:coverage:check"),
          scriptCommand("npm-run-all test:coverage:clean test:coverage:copy:* test:coverage:merge test:coverage:report")
        )
        .addScript(scriptKey("test:coverage:clean"), scriptCommand("rimraf target/.nyc_output"))
        .addScript(
          scriptKey("test:coverage:copy:unit"),
          scriptCommand(
            "cpy --flat target/frontend-coverage/unit-tests/coverage-final.json target/.nyc_output/ --rename=unit-coverage-final.json"
          )
        )
        .addScript(
          scriptKey("test:coverage:copy:component"),
          scriptCommand(
            "cpy --flat target/frontend-coverage/components-tests/coverage-final.json target/.nyc_output/ --rename=component-coverage-final.json"
          )
        )
        .addScript(
          scriptKey("test:coverage:merge"),
          scriptCommand("nyc merge target/.nyc_output target/.nyc_output/frontend-combined-coverage.json")
        )
        .addScript(scriptKey("test:coverage:report"), scriptCommand("nyc report --reporter=lcov --reporter=text"))
      .and()
      .apply(patchViteConfig())
      .apply(patchVitestConfig())
      .apply(patchCypressConfig(properties))
      .files()
        .batch(SOURCE, to("."))
          .addTemplate(".nycrc.json")
        .and()
        .batch(SOURCE.append(CYPRESS_COMPONENT_TESTS), to(CYPRESS_COMPONENT_TESTS))
          .addTemplate(".nycrc.json")
        .and()
        .batch(SOURCE.append(CYPRESS_COMPONENT_TESTS).append("support"), to(CYPRESS_COMPONENT_TESTS).append("/support"))
          .addFile("component-tests.ts")
        .and()
      .and()
      .build();
    // @formatter:on
  }

  private Consumer<JHipsterModule.JHipsterModuleBuilder> patchCypressConfig(JHipsterModuleProperties properties) {
    return moduleBuilder ->
      moduleBuilder
        .mandatoryReplacements()
        .in(path(CYPRESS_COMPONENT_TESTS + "/cypress-config.ts"))
        .add(fileStart(), "import registerCodeCoverageTasks from '@cypress/code-coverage/task';\n")
        .add(
          lineAfterRegex("e2e:"),
          properties.indentation().times(2)
          + """
          setupNodeEvents(on, config) {
                registerCodeCoverageTasks(on, config);
                return config;
              },"""
        )
        .add(text("supportFile: false"), "supportFile: 'src/test/webapp/component/support/component-tests.ts'");
  }

  private Consumer<JHipsterModule.JHipsterModuleBuilder> patchVitestConfig() {
    return moduleBuilder ->
      moduleBuilder
        .mandatoryReplacements()
        .in(path("vitest.config.ts"))
        .add(regex("reportsDirectory: '(.*?)/test-results/'"), "reportsDirectory: 'target/frontend-coverage/unit-tests/'");
  }

  private Consumer<JHipsterModule.JHipsterModuleBuilder> patchViteConfig() {
    return moduleBuilder ->
      moduleBuilder
        .mandatoryReplacements()
        .in(path("vite.config.ts"))
        .add(lineAfterRegex("from 'vite'"), "import istanbul from 'vite-plugin-istanbul';")
        .add(text("plugins: ["), "plugins: [istanbul(), ");
  }
}
