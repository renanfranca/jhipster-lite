package tech.jhipster.lite.shared.github.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.lite.shared.github.application.GithubApplicationService;
import tech.jhipster.lite.shared.github.domain.authentication.GithubToken;

@RestController
@RequestMapping("/api")
@Tag(name = "Github")
class GithubResource {

  private final GithubApplicationService github;

  public GithubResource(GithubApplicationService github) {
    this.github = github;
  }

  @GetMapping("github/oauth2/authorization")
  @Operation(summary = "Get Github authorization URL")
  public String getAuthorizationUrl() {
    return github.getAuthorizationUrl().url();
  }

  @GetMapping("github/oauth2/callback")
  @Operation(summary = "Handle Github OAuth2 callback")
  public ResponseEntity<RestGithubToken> handleCallback(@RequestParam String code) {
    return ResponseEntity.ok(RestGithubToken.from(github.authenticate(code)));
  }

  @GetMapping("github/organizations")
  @Operation(summary = "List Github organizations for authenticated user")
  public ResponseEntity<List<RestGithubOrganization>> listOrganizations(@RequestHeader("Authorization") String authorization) {
    GithubToken token = new GithubToken(authorization.replace("Bearer ", ""), "Bearer");

    return ResponseEntity.ok(github.listUserOrganizations(token).stream().map(RestGithubOrganization::from).toList());
  }
}
