package tech.jhipster.lite.generator.client.paginationdomain.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.paginationdomain.domain.TSPaginationDomainModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class TsPaginationDomainApplicationService {

  private static final TSPaginationDomainModuleFactory factory = new TSPaginationDomainModuleFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
