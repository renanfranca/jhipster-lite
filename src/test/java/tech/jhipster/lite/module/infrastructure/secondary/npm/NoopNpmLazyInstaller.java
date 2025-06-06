package tech.jhipster.lite.module.infrastructure.secondary.npm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.module.domain.npm.NpmLazyInstaller;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public class NoopNpmLazyInstaller implements NpmLazyInstaller {

  private static final Logger log = LoggerFactory.getLogger(NoopNpmLazyInstaller.class);

  @Override
  public void runInstallIn(JHipsterProjectFolder folder) {
    log.info("Simulating installation of npm dependencies");
  }
}
