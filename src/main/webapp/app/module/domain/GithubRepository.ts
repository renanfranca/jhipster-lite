export interface GithubAuthorizationUrl {
  url: string;
}

export interface GithubToken {
  accessToken: string;
  tokenType: string;
}

export interface GithubRepository {
  getAuthorizationUrl(): Promise<GithubAuthorizationUrl>;
  authenticate(code: string): Promise<GithubToken>;
}
