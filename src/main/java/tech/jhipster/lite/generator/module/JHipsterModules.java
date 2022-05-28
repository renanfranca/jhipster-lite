package tech.jhipster.lite.generator.module;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.generator.module.domain.Indentation;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;

@Service
public class JHipsterModules {

  private JHipsterModulesApplicationService modules;

  public JHipsterModules(JHipsterModulesApplicationService modules) {
    this.modules = modules;
  }

  public void apply(JHipsterModule module) {
    modules.apply(Indentation.DEFAULT, module);
  }

  public void apply(Indentation indentation, JHipsterModule module) {
    modules.apply(indentation, module);
  }
}
