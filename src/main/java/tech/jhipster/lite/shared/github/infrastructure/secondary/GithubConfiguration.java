package tech.jhipster.lite.shared.github.infrastructure.secondary;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tech.jhipster.lite.shared.github.domain.oauth.GithubOauth2Configuration;
import tech.jhipster.lite.shared.github.domain.oauth.GithubScope;

@Configuration
class GithubConfiguration {

  @Value("${github.client-id}")
  private String clientId;

  @Value("${github.client-secret}")
  private String clientSecret;

  @Value("${github.redirect-uri}")
  private String redirectUri;

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  GithubOauth2Configuration githubOauth2Configuration() {
    return new GithubOauth2Configuration(
      clientId,
      clientSecret,
      redirectUri,
      Set.of(GithubScope.READ_REPO, GithubScope.READ_USER, GithubScope.READ_ORG, GithubScope.READ_WORKFLOW)
    );
  }
}
