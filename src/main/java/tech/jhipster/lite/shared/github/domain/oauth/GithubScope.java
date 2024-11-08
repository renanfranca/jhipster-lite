package tech.jhipster.lite.shared.github.domain.oauth;

public enum GithubScope {
//  USER_EMAIL("user:email"),
//  READ_USER("read:user"),
//  READ_REPO("read:repo"),
//  READ_ORG("read:org");
  READ_USER("user"),
  READ_REPO("repo"),
  READ_ORG("org"),
  READ_WORKFLOW("workflow");

  private final String scope;

  GithubScope(String scope) {
    this.scope = scope;
  }

  public String get() {
    return scope;
  }
}
