package tech.jhipster.lite.shared.github.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.shared.github.application.GithubApplicationService;

@RestController
@RequestMapping("/api/github")
@Tag(name = "Github")
class GithubResource {

  private final GithubApplicationService github;

  public GithubResource(GithubApplicationService github) {
    this.github = github;
  }

  @GetMapping("/oauth2/authorization")
  @Operation(summary = "Get Github authorization URL")
  ResponseEntity<String> getAuthorizationUrl() {
    return ResponseEntity.ok(github.getAuthorizationUrl());
  }

  @GetMapping("/oauth2/callback")
  @Operation(summary = "Handle Github OAuth2 callback")
  ResponseEntity<RestGithubUser> handleCallback(@RequestParam String code) {
    return ResponseEntity.ok(RestGithubUser.from(github.authenticate(code)));
  }
}
