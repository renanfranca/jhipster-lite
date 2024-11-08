package tech.jhipster.lite.shared.github.infrastructure.primary;

import tech.jhipster.lite.shared.github.domain.GithubOrganization;

record RestGithubOrganization(String login, String name, String avatarUrl) {
  public static RestGithubOrganization from(GithubOrganization organization) {
    return new RestGithubOrganization(organization.login(), organization.name(), organization.avatarUrl());
  }
}
