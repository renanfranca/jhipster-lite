package tech.jhipster.lite.generator.server.springboot.mvc.web.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootMvcApplicationService {

  private final SpringBootMvcModuleFactory springBootMvcs;

  public SpringBootMvcApplicationService() {
    springBootMvcs = new SpringBootMvcModuleFactory();
  }

  public JHipsterModule buildTomcatModule(JHipsterModuleProperties properties) {
    return springBootMvcs.buildTomcatModule(properties);
  }

  public JHipsterModule buildUndertowModule(JHipsterModuleProperties properties) {
    return springBootMvcs.buildUndertowModule(properties);
  }

  public JHipsterModule buildEmptyModule(JHipsterModuleProperties properties) {
    return springBootMvcs.buildEmptyModule(properties);
  }
}
