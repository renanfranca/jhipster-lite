package tech.jhipster.lite.shared.github.domain.oauth;

import java.util.Set;
import tech.jhipster.lite.shared.error.domain.Assert;

public record GithubOauth2Configuration(String clientId, String clientSecret, String redirectUri, Set<GithubScope> scopes) {
  public GithubOauth2Configuration {
    Assert.notBlank("clientId", clientId);
    Assert.notBlank("clientSecret", clientSecret);
    Assert.notBlank("redirectUri", redirectUri);
    Assert.notEmpty("scopes", scopes);
  }

  public String buildAuthorizationUrl() {
    String scopeString = String.join(",", scopes.stream().map(GithubScope::get).toList());
    return String.format(
      "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s",
      clientId,
      redirectUri,
      scopeString
    );
  }
}
