package tech.jhipster.lite.shared.github.infrastructure.secondary;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import tech.jhipster.lite.shared.github.domain.GithubRepository;
import tech.jhipster.lite.shared.github.domain.authentication.GithubAuthenticationCode;
import tech.jhipster.lite.shared.github.domain.authentication.GithubAuthorizationUrl;
import tech.jhipster.lite.shared.github.domain.authentication.GithubToken;
import tech.jhipster.lite.shared.github.domain.oauth.GithubOauth2Configuration;

@SuppressWarnings("java:S6212")
@Repository
public class HttpGithubRepository implements GithubRepository {

  private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";

  private final RestTemplate restTemplate;
  private final GithubOauth2Configuration configuration;

  public HttpGithubRepository(RestTemplate restTemplate, GithubOauth2Configuration configuration) {
    this.restTemplate = restTemplate;
    this.configuration = configuration;
  }

  @Override
  public GithubAuthorizationUrl buildAuthorizationUrl() {
    return GithubAuthorizationUrl.from(configuration.buildAuthorizationUrl());
  }

  @Override
  public GithubToken authenticate(GithubAuthenticationCode code) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

    var body = new RestGithubTokenRequest(configuration.clientId(), configuration.clientSecret(), code.code());

    var request = new HttpEntity<>(body, headers);
    //TODO - use RestClient
    ResponseEntity<RestGithubTokenResponse> response = restTemplate.postForEntity(GITHUB_TOKEN_URL, request, RestGithubTokenResponse.class);

    //TODO - fix this potential null point exception check for exception and treat it with specific error
    return response.getBody().toDomain();
  }
}
