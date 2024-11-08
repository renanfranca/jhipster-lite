type GithubOrganizationLogin = string;
type GithubOrganizationName = string;
type GithubOrganizationAvatarUrl = string;

export type GithubOrganization = {
  login: GithubOrganizationLogin;
  name: GithubOrganizationName;
  avatarUrl: GithubOrganizationAvatarUrl;
}
