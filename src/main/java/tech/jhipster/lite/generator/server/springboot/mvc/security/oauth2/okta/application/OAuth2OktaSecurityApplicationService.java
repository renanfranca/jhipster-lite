package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.okta.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.okta.domain.OAuth2OktaModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class OAuth2OktaSecurityApplicationService {

  private final OAuth2OktaModuleFactory oAuth2Okta;

  public OAuth2OktaSecurityApplicationService() {
    oAuth2Okta = new OAuth2OktaModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return oAuth2Okta.buildModule(properties);
  }
}
