package tech.jhipster.lite.shared.github.application;

import java.util.List;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.shared.github.domain.GithubOrganization;
import tech.jhipster.lite.shared.github.domain.GithubRepository;
import tech.jhipster.lite.shared.github.domain.authentication.GithubAuthenticationCode;
import tech.jhipster.lite.shared.github.domain.authentication.GithubAuthorizationUrl;
import tech.jhipster.lite.shared.github.domain.authentication.GithubToken;

@Service
public class GithubApplicationService {

  private final GithubRepository github;

  public GithubApplicationService(GithubRepository github) {
    this.github = github;
  }

  public GithubAuthorizationUrl getAuthorizationUrl() {
    return github.buildAuthorizationUrl();
  }

  public GithubToken authenticate(String code) {
    return github.authenticate(new GithubAuthenticationCode(code));
  }

  public List<GithubOrganization> listUserOrganizations(GithubToken token) {
    return github.listUserOrganizations(token);
  }
}
