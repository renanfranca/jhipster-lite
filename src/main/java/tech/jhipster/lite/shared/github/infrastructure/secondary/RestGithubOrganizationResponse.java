package tech.jhipster.lite.shared.github.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.shared.github.domain.GithubOrganization;

record RestGithubOrganizationResponse(String login, String name, @JsonProperty("avatar_url") String avatarUrl) {
  public GithubOrganization toDomain() {
    return new GithubOrganization(login, name, avatarUrl);
  }
}
