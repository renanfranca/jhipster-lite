package tech.jhipster.lite.shared.github.domain;

import tech.jhipster.lite.shared.error.domain.Assert;

public record GithubOrganization(String login, String name, String avatarUrl) {
  public GithubOrganization {
    Assert.notBlank("login", login);
    Assert.notBlank("name", name);
  }
}
